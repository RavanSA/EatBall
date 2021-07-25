package com.project.eatball;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<ShareUtils> shareUtils;

    public CustomRecyclerAdapter(Context context, List shareUtils) {
        this.context = context;
        this.shareUtils = shareUtils;
    }

    @Override
    public CustomRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(shareUtils.get(position));

        ShareUtils pu = shareUtils.get(position);

        holder.pName.setText(pu.getSocialmedia());
        holder.pJobProfile.setImageResource(pu.getDescription());

    }

    @Override
    public int getItemCount() {
        return shareUtils.size();
    }
    public void openWebURL( String inURL ) {
        Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( inURL ) );
       context.startActivity(browse);
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView pName;
        public ImageView pJobProfile;

        public ViewHolder(View itemView) {
            super(itemView);

            pName = (TextView) itemView.findViewById(R.id.pNametxt);
            pJobProfile = (ImageView) itemView.findViewById(R.id.pJobProfiletxt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ShareUtils cpu = (ShareUtils) view.getTag();
                    if(cpu.getSocialmedia().equals("Facebook")) {
                        Toast.makeText(view.getContext(), "Redirecting to "+cpu.getSocialmedia()+"...", Toast.LENGTH_SHORT).show();
                        openWebURL("http://www.facebook.com");

                    }
                    if(cpu.getSocialmedia().equals("Instagram")){
                        Toast.makeText(view.getContext(), "Redirecting to "+cpu.getSocialmedia()+"...", Toast.LENGTH_SHORT).show();

                        openWebURL("http://www.instagram.com");

                    }
                    if(cpu.getSocialmedia().equals("Twitter")){
                        Toast.makeText(view.getContext(), "Redirecting to "+cpu.getSocialmedia()+"...", Toast.LENGTH_SHORT).show();
                        openWebURL("http://www.twitter.com");

                    }
                    if(cpu.getSocialmedia().equals("Pinterest")){
                        Toast.makeText(view.getContext(), "Redirecting to "+cpu.getSocialmedia()+"...", Toast.LENGTH_SHORT).show();
                        openWebURL("http://www.pinterest.com");

                    }
                    if(cpu.getSocialmedia().equals("Messenger")){
                        Toast.makeText(view.getContext(), "Redirecting to "+cpu.getSocialmedia()+"...", Toast.LENGTH_SHORT).show();
                        openWebURL("http://www.messenger.com");
                    }

                }
            });

        }

    }

}
