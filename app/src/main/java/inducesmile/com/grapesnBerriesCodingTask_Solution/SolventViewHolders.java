package inducesmile.com.grapesnBerriesCodingTask_Solution;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SolventViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView Productprice;
    public TextView Productdesc;
    public ImageView Productphoto;

    public SolventViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        Productprice = (TextView) itemView.findViewById(R.id.product_price);
        Productdesc = (TextView) itemView.findViewById(R.id.product_desc);
        Productphoto = (ImageView) itemView.findViewById(R.id.product_photo);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
