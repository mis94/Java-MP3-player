import javax.swing.JLabel;
import javax.swing.JSlider;

import javafx.scene.media.MediaPlayer;

public class EqualizerPresets {

	public String[] name = new String[18];
	Float[][] Presets = new Float[18][];

	public EqualizerPresets() {

		{
			name[0] = "Normal";
			Presets[0] = new Float[] { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };
		}
		{
			name[1] = "classical";
			Presets[1] = new Float[] { -1.11022e-15f, -1.11022e-15f, -1.11022e-15f, -1.11022e-15f, -1.11022e-15f,
					-1.11022e-15f, -7.2f, -7.2f, -7.2f, -9.6f };
		}
		{
			name[2] = "club";
			Presets[2] = new Float[] { -1.11022e-15f, -1.11022e-15f, 8.0f, 5.6f, 5.6f, 5.6f, 3.2f, -1.11022e-15f,
					-1.11022e-15f, -1.11022e-15f };
		}
		{
			name[3] = "dance";
			Presets[3] = new Float[] { 9.6f, 7.2f, 2.4f, -1.11022e-15f, -1.11022e-15f, -5.6f, -7.2f, -7.2f,
					-1.11022e-15f, -1.11022e-15f };
		}
		{
			name[4] = "fullbass";
			Presets[4] = new Float[] { -8.0f, 9.6f, 9.6f, 5.6f, 1.6f, -4.0f, -8.0f, -10.4f, -11.2f, -11.2f };
		}
		{
			name[5] = "fullbasstreble";
			Presets[5] = new Float[] { 7.2f, 5.6f, -1.11022e-15f, -7.2f, -4.8f, 1.6f, 8.0f, 11.2f, 12.0f, 12.0f };
		}
		{
			name[6] = "fulltreble";
			Presets[6] = new Float[] { -9.6f, -9.6f, -9.6f, -4.0f, 2.4f, 11.2f, 16.0f, 16.0f, 16.0f, 16.8f };
		}
		{
			name[7] = "headphones";
			Presets[7] = new Float[] { 4.8f, 11.2f, 5.6f, -3.2f, -2.4f, 1.6f, 4.8f, 9.6f, 12.8f, 14.4f };
		}
		{
			name[8] = "largehall";
			Presets[8] = new Float[] { 10.4f, 10.4f, 5.6f, 5.6f, -1.11022e-15f, -4.8f, -4.8f, -4.8f, -1.11022e-15f,
					-1.11022e-15f };
		}
		{
			name[9] = "live";
			Presets[9] = new Float[] { -4.8f, -1.11022e-15f, 4.0f, 5.6f, 5.6f, 5.6f, 4.0f, 2.4f, 2.4f, 2.4f };
		}
		{
			name[10] = "party";
			Presets[10] = new Float[] { 7.2f, 7.2f, -1.11022e-15f, -1.11022e-15f, -1.11022e-15f, -1.11022e-15f,
					-1.11022e-15f, -1.11022e-15f, 7.2f, 7.2f };
		}
		{
			name[11] = "pop";
			Presets[11] = new Float[] { -1.6f, 4.8f, 7.2f, 8.0f, 5.6f, -1.11022e-15f, -2.4f, -2.4f, -1.6f, -1.6f };
		}
		{
			name[12] = "reggae";
			Presets[12] = new Float[] { -1.11022e-15f, -1.11022e-15f, -1.11022e-15f, -5.6f, -1.11022e-15f, 6.4f, 6.4f,
					-1.11022e-15f, -1.11022e-15f, -1.11022e-15f };
		}
		{
			name[13] = "rock";
			Presets[13] = new Float[] { 8.0f, 4.8f, -5.6f, -8.0f, -3.2f, 4.0f, 8.8f, 11.2f, 11.2f, 11.2f };
		}
		{
			name[14] = "ska";
			Presets[14] = new Float[] { -2.4f, -4.8f, -4.0f, -1.11022e-15f, 4.0f, 5.6f, 8.8f, 9.6f, 11.2f, 9.6f };
		}
		{
			name[15] = "soft";
			Presets[15] = new Float[] { 4.8f, 1.6f, -1.11022e-15f, -2.4f, -1.11022e-15f, 4.0f, 8.0f, 9.6f, 11.2f,
					12.0f };
		}
		{
			name[16] = "softrock";
			Presets[16] = new Float[] { 4.0f, 4.0f, 2.4f, -1.11022e-15f, -4.0f, -5.6f, -3.2f, -1.11022e-15f, 2.4f,
					8.8f };
		}
		{
			name[17] = "techno";
			Presets[17] = new Float[] { 8.0f, 5.6f, -1.11022e-15f, -5.6f, -4.8f, -1.11022e-15f, 8.0f, 9.6f, 9.6f,
					8.8f };
		}
	}
    void next_Mode(int index,JSlider bands[],MediaPlayer mediaPlayer ,JLabel name)
    {
    	index=index%18;
    	name.setText(this.name[index]);   

    	double previous_val,next_val;
    	for(int i=0;i<10;i++)
    	{
    		
    		
    	    previous_val=mediaPlayer.getAudioEqualizer().getBands().get(i).MAX_GAIN*2;
    	    mediaPlayer.getAudioEqualizer().getBands().get(i).setGain(Presets[index][i]);
    	    next_val=mediaPlayer.getAudioEqualizer().getBands().get(i).getGain();
    	    
    	    
    	    bands[i].setValue((int)(Math.abs(next_val/previous_val)*100));
    	}
		
    }
    void previous_Mode(int index,JSlider bands[],MediaPlayer mediaPlayer ,JLabel name)
    {
    	index=(index<0?17:index);
    	name.setText(this.name[index]);   

    	double previous_val,next_val;
    	for(int i=0;i<10;i++)
    	{
    	    previous_val=mediaPlayer.getAudioEqualizer().getBands().get(i).MAX_GAIN*2;
    	    mediaPlayer.getAudioEqualizer().getBands().get(i).setGain(Presets[index][i]);
    	    next_val=mediaPlayer.getAudioEqualizer().getBands().get(i).getGain();
    	    
    	    
    	    bands[i].setValue((int)(Math.abs(next_val/previous_val)*100));
    	}
		
    }
}
