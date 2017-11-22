package com.thewhitewalkers.parkhere;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class InboxAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public InboxAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                SentRequests sentRequestsTab = new SentRequests();
                return sentRequestsTab;
            case 1:
                PendingRequests pendingRequestsTab = new PendingRequests();
                return pendingRequestsTab;
            case 2:
                AcceptedRequests acceptedRequestsTab = new AcceptedRequests();
                return acceptedRequestsTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
