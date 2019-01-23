package fandy.dev.debtcollector;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import fandy.dev.debtcollector.GameMain;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		config.useWakelock = true;
		initialize(new GameMain(), config);
		if(getContext().checkCallingOrSelfPermission("android.permission.INTERNET") == PackageManager.PERMISSION_GRANTED) {
			Gdx.app.log("INFO", "PERMISSION GRANTED");
		} else {
			Gdx.app.log("ERROR", "PERMISSION DENIED");
		}
		//Helo Fandy APA KABAR ?
		//Dota jgn lupa ntar malem
	}
}
