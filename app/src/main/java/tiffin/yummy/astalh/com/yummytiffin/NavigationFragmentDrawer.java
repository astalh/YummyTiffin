package tiffin.yummy.astalh.com.yummytiffin;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragmentDrawer extends Fragment {

    private static String PREF_FILE_NAME = "YummyTiffinsSharedPref";


    private String mUserLearnedKey = "user_learned_drawer";

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View contentView;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    public NavigationFragmentDrawer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mUserLearnedDrawer = Boolean.valueOf(getPreferenceValue(getActivity(), mUserLearnedKey,"false"));

        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_fragment_drawer, container, false);
    }


    public void setUp(int fragmentId, final DrawerLayout drawerLayout, Toolbar toolbar) {

        final View fragmentView = getActivity().findViewById(R.id.main_nav_fragment);
        mDrawerLayout = drawerLayout;
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    saveToPreferences(getActivity(), mUserLearnedKey,mUserLearnedDrawer +"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(fragmentView);
        }

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }

    private static void saveToPreferences(Context context, String preferenceKey, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceKey,preferenceValue);
        editor.apply();
    }


    private static String getPreferenceValue(Context context, String preferenceKey, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceKey,defaultValue);
    }

}
