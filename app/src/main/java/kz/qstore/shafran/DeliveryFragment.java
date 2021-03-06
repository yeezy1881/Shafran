package kz.qstore.shafran;



import android.os.Bundle;
import android.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DeliveryFragment extends Fragment {


    public DeliveryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.delivery);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(display.getWidth(), display.getWidth()/16*9);
        imageView.setLayoutParams(parms);
        return view;
    }

}
