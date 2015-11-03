package cs301.cannon;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;



/**
 * CannonMainActivity
 * 
 * This is the activity for the cannon animation. It creates an AnimationCanvas which contains a Cannon Animator.
 *
 * Description of App: PART B
 * The user can designate what velocity, gravity and angle to shoot at, if any of the black targets on screen are hit, the user
 * will gain a point, If the user is not happy with his shot, or if it is taking too long for the shot to leave the screen, the user
 * can reset. The reset functionality reloads the cannon, and creates a new cannon if it was previously destroyed.
 *
 * ADDITIONS FOR PART B:
 *
 * In the Cannon
 * 
 * @author Daniel Rothschilds
 * @version November 1st 2015
 * 
 */
public class CannonMainActivity extends Activity {

	//instance variables
	public Animator cannon;		//cannon Animator to shoot
	public SoundPool soundPool;	//soundpool to play explosion noise
	public int explosion;		//to hold the id of the explosion noise

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cannon_main);

		// Create an animation canvas and place it in the main layout
		cannon = new Cannon();
		AnimationCanvas myCanvas = new AnimationCanvas(this, cannon);
		LinearLayout mainLayout = (LinearLayout) this.findViewById(R.id.topLevelLayout);
		mainLayout.addView(myCanvas);

		//create a soundpool and load the explosion noise
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		explosion = soundPool.load(this, R.raw.explosion, 1);








	}
	/**
	 * This method will fire a shot at the designated angle, velocity and gravity
	 *
	 * return: void
	 */

	public void fire(View view)
	{
		//Play the sound upon firing if the cannon is not destroyed
		if(!Cannon.isHit)
			soundPool.play(explosion, 1f, 1f, 1, 0, 1.0f);


		EditText velocityNum =(EditText) this.findViewById(R.id.velocity);	//Get the inputed velocity
		EditText thetaNum =(EditText) this.findViewById(R.id.theta);		//Get the inputed Angle
		EditText gravityNum =(EditText) this.findViewById(R.id.gravity);	//Get the inputed gravity

		//convert values to double to be apple to create a shot
		double velocity = Double.parseDouble(velocityNum.getText().toString());
		double gravity = Double.parseDouble(gravityNum.getText().toString());
		double theta = Double.parseDouble(thetaNum.getText().toString());
		//if the user inputs invalid data reset to 45 degrees, 50 for velocity, and 1.962 for gravity
		if(theta<0 || theta > 90)
			theta = 45;

		if(gravity<0 || gravity>25)
			gravity = 1.962;

		if (velocity <=  0)
			velocity = 50;
		//Reset the values if any were invalid
		gravityNum.setText(gravity + "");
		velocityNum.setText(velocity +"");
		thetaNum.setText(theta + "");
		//fire the cannon
		cannon.reloadAndFire(velocity, theta*3.1415/180, gravity);

	}


	//when user clicks reset, will call cannon reset to reset shot
	public void reset(View view)
	{
			cannon.reset();
	}



	/**
	 * This is the default behavior (empty menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cannon_main, menu);
		return true;
	}
}
