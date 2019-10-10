package org.techtown.sttampproject.MyPage;

import android.widget.Filter;

import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    MypageAdapter adapter;
    ArrayList<SharingDataClass> filterList;

    public CustomFilter(ArrayList<SharingDataClass> filterList,MypageAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<SharingDataClass> filteredPets=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getUser_id().toUpperCase().equals(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPets.add(filterList.get(i));
                }
            }

            results.count=filteredPets.size();
            results.values=filteredPets;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.mList= (ArrayList<SharingDataClass>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();

    }
}