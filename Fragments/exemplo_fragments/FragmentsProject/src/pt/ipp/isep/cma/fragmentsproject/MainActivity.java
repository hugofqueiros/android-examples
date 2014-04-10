package pt.ipp.isep.cma.fragmentsproject;

import pt.ipp.isep.cma.fragmentsproject.FirstFragment.OnPersonSelectedListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity implements OnPersonSelectedListener {

	private FirstFragment mFirstFragment;
	private SecondFragment mSecondFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null)
                return;

            mFirstFragment = new FirstFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mFirstFragment).commit();
        }
	}

	@Override
	public void onPersonSelected(Person p) {
        mSecondFragment = (SecondFragment) getSupportFragmentManager().findFragmentById(R.id.b_fragment);

        if(mSecondFragment != null) {
            mSecondFragment.setPerson(p);
        } else {
        	mSecondFragment = new SecondFragment();
        	mSecondFragment.setPerson(p);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, mSecondFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }	
	}
}
