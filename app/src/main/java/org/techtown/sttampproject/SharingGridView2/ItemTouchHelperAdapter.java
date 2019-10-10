package org.techtown.sttampproject.SharingGridView2;

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);


}
