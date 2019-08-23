package layout;

/**
 * Created by enmanuel on 28/06/16.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 5;
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int arg0) {

        MyFragmentLayout myFragment = new MyFragmentLayout();
        Bundle data = new Bundle();
        data.putInt("current_page", arg0+1);
        myFragment.setArguments(data);
        return myFragment;
    }

    /** Returns the number of pages */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return "Pregunta #" + ( position + 1 );
//    }
}
