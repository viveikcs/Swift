package com.example.swiftdelivery.agent;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swiftdelivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AgentAvailableDeliveryFragment extends Fragment {

    TextView tv_heading, tv_noDeliveries;
    private RecyclerView deliveryRecyclerView;
    private DatabaseReference reference, agentDetailsReference;
    private FirebaseUser currentAgent;
    private AgentAvailableDeliveryAdapter adapter;
    private List<AgentAvailableDelivery> deliveryList = new ArrayList<>();
    private String agentName, agentMobile, agentVehicleType, agentVehicleReg, deliveryId;
    private String delv_ID;
    private ValueEventListener valueEventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_agent_available_delivery, container, false);

        currentAgent = FirebaseAuth.getInstance().getCurrentUser();
        if (currentAgent == null) {
            Toast.makeText(getContext(), "You are not logged in. Please log in to continue.", Toast.LENGTH_SHORT).show();
            Intent intent_goBack = new Intent(requireContext(), AgentLoginActivity.class);
            startActivity(intent_goBack);
        }

        reference = FirebaseDatabase.getInstance().getReference("Deliveries");

        if (currentAgent != null) {
            String agentId = currentAgent.getUid();
            agentDetailsReference = FirebaseDatabase.getInstance().getReference("Delivery Agents").child(agentId);
            agentDetailsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        agentName = snapshot.child("name").getValue(String.class);
                        agentMobile = snapshot.child("phone").getValue(String.class);
                        agentVehicleType = snapshot.child("vehicle").getValue(String.class);
                        agentVehicleReg = snapshot.child("registration").getValue(String.class);
                    } else {
                        Toast.makeText(getContext(), "Agent details not found.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Agent does not exist.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        tv_heading = view.findViewById(R.id.tv_availableDeliveries);
        tv_noDeliveries = view.findViewById(R.id.tv_noDeliveries);


        deliveryRecyclerView = view.findViewById(R.id.availableDeliveryRecyclerView);
        deliveryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        checkAgentAssigned();

        adapter = new AgentAvailableDeliveryAdapter(deliveryList, new AgentAvailableDeliveryAdapter.OnDeliveryClickListener() {
            @Override
            public void onDeliveryClick(AgentAvailableDelivery delivery) {
                acceptDelivery(delivery);
            }
        });

        deliveryRecyclerView.setAdapter(adapter);

        return view;
    }

    private void fetchAvailableDeliveries()
    {
        reference.orderByChild("Status").equalTo("pending").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                deliveryList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.d("FirebaseSnapshot", "Data: " + snapshot.getValue());
                    AgentAvailableDelivery delivery = snapshot.getValue(AgentAvailableDelivery.class);
                    if (delivery != null)
                    {
                        delivery.setDeliveryID(snapshot.getKey());
                        deliveryList.add(delivery);
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Failed to retrieve delivery with null deliveryId", Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.updateDeliveries(deliveryList);

                if (deliveryList.isEmpty()) {
                    deliveryRecyclerView.setVisibility(View.GONE);
                    tv_noDeliveries.setVisibility(View.VISIBLE);
                } else {
                    deliveryRecyclerView.setVisibility(View.VISIBLE);
                    tv_noDeliveries.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load available deliveries.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void acceptDelivery(AgentAvailableDelivery delivery)
    {
        if (currentAgent != null)
        {
            String agentId = currentAgent.getUid();

            reference.child(delivery.getDeliveryID()).child("Status").setValue("accepted");
            reference.child(delivery.getDeliveryID()).child("AssignedAgent").setValue(agentId);
            reference.child(delivery.getDeliveryID()).child("AssignedAgentName").setValue(agentName);
            reference.child(delivery.getDeliveryID()).child("AssignedAgentMobile").setValue(agentMobile);
            reference.child(delivery.getDeliveryID()).child("AssignedAgentVehicleType").setValue(agentVehicleType);
            reference.child(delivery.getDeliveryID()).child("AssignedAgentVehicleRegistration").setValue(agentVehicleReg);
            Toast.makeText(getContext(), "Delivery accepted !", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAgentAssigned()
    {
        String agentId = currentAgent.getUid();
        valueEventListener =  reference.orderByChild("AssignedAgent").equalTo(agentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        String status = snapshot.child("Status").getValue(String.class);
                        if (!"Delivered".equalsIgnoreCase(status))
                        {
                            delv_ID = snapshot.getKey();

                            AgentDeliveryNavigationFragment navigationFragment =  new AgentDeliveryNavigationFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("DELIVERY_ID", delv_ID);
                            navigationFragment.setArguments(bundle);
                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_agent, navigationFragment).addToBackStack(null).commit();
                            return;
                        }
                    }
                }
                fetchAvailableDeliveries();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to check assigned deliveries", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        checkAgentAssigned();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (reference != null) {
            reference.removeEventListener(valueEventListener);
        }
    }
}
