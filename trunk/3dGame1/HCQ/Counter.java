package HCQ;
import com.sun.opengl.util.awt.TextRenderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.*;
import java.text.*;

import javax.media.opengl.*;


/** A simple class which uses the TextRenderer to provide an FPS
    counter overlaid on top of the scene. */

public class Counter {
  // Placement constants
  public static final int UPPER_LEFT  = 1;
  public static final int UPPER_RIGHT = 2;
  public static final int LOWER_LEFT  = 3;
  public static final int LOWER_RIGHT = 4;

  private int textLocation = LOWER_RIGHT;
  private GLDrawable drawable;
  private TextRenderer renderer;
  private DecimalFormat format = new DecimalFormat("####.00");
  private int frameCount;
  private long startTime;
  private String Text;
  private int fpsMagnitude;
  private int fpsWidth;
  private int fpsHeight;
  private int fpsOffset;
  
  public String Intextup = "������:";
  public String Intextdown = "��Ϣ:";
  private int xsit;
  private int ysit;
  
  //����
  public float bulletX;
  public float bulletY;
  public float bulletZ;
  
  public String XJG = "";
  

//�õ���Ʒ��ʾ
  public int inforYN = 0;
   public String Strinfor;
  
  /** Creates a new FPSCounter with the given font size. An OpenGL
      context must be current at the time the constructor is called.

      @param drawable the drawable to render the text to
      @param textSize the point size of the font to use
      @throws GLException if an OpenGL context is not current when the constructor is called
  */
  public Counter(GLDrawable drawable, int textSize) throws GLException {
    this(drawable, new Font("SansSerif", Font.BOLD, textSize));
  }

  /** Creates a new FPSCounter with the given font. An OpenGL context
      must be current at the time the constructor is called.

      @param drawable the drawable to render the text to
      @param font the font to use
      @throws GLException if an OpenGL context is not current when the constructor is called
  */
  public Counter(GLDrawable drawable, Font font) throws GLException {
    this(drawable, font, true, true);
  }

  /** Creates a new FPSCounter with the given font and rendering
      attributes. An OpenGL context must be current at the time the
      constructor is called.

      @param drawable the drawable to render the text to
      @param font the font to use
      @param antialiased whether to use antialiased fonts
      @param useFractionalMetrics whether to use fractional font
      @throws GLException if an OpenGL context is not current when the constructor is called
  */
  public Counter(GLDrawable drawable,
                    Font font,
                    boolean antialiased,
                    boolean useFractionalMetrics) throws GLException {
    this.drawable = drawable;
    renderer = new TextRenderer(font, antialiased, useFractionalMetrics);
  }

  /** Gets the relative location where the text of this FPSCounter
      will be drawn: one of UPPER_LEFT, UPPER_RIGHT, LOWER_LEFT, or
      LOWER_RIGHT. Defaults to LOWER_RIGHT. */
  public int getTextLocation() {
    return textLocation;
  }

  /** Sets the relative location where the text of this FPSCounter
      will be drawn: one of UPPER_LEFT, UPPER_RIGHT, LOWER_LEFT, or
      LOWER_RIGHT. Defaults to LOWER_RIGHT. */
  public void setTextLocation(int textLocation) {
    if (textLocation < UPPER_LEFT || textLocation > LOWER_RIGHT) {
      throw new IllegalArgumentException("textLocation");
    }
    this.textLocation = textLocation;
  }

  /** Changes the current color of this TextRenderer to the supplied
      one, where each component ranges from 0.0f - 1.0f. The alpha
      component, if used, does not need to be premultiplied into the
      color channels as described in the documentation for {@link
      Texture Texture}, although premultiplied colors are used
      internally. The default color is opaque white.

      @param r the red component of the new color
      @param g the green component of the new color
      @param b the blue component of the new color
      @param alpha the alpha component of the new color, 0.0f =
        completely transparent, 1.0f = completely opaque
      @throws GLException If an OpenGL context is not current when this method is called
  */
  public void setColor(float r, float g, float b, float a) throws GLException {
    renderer.setColor(r, g, b, a);
  }

  /** Updates the FPSCounter's internal timer and counter and draws
      the computed FPS. It is assumed this method will be called only
      once per frame.
  */
  public void draw() {
    if (startTime == 0) {
      startTime = System.currentTimeMillis();
    }

    if (++frameCount >= 100) {
      long endTime = System.currentTimeMillis();
      float fps = 100.0f / (float) (endTime - startTime) * 1000;
      recomputeFPSSize(fps);
      frameCount = 0;
      startTime = System.currentTimeMillis();

      Text = "FPS: " + format.format(fps);
    }

    if (Text != null) {
      renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
      // Figure out the location at which to draw the text
      int x = 0;
      int y = 0;
      switch (textLocation) {
        case UPPER_LEFT:
          x = fpsOffset;
          y = drawable.getHeight() - fpsHeight - fpsOffset;
          break;

        case UPPER_RIGHT:
          x = drawable.getWidth() - fpsWidth - fpsOffset;
          y = drawable.getHeight() - fpsHeight - fpsOffset;
          break;

        case LOWER_LEFT:
          x = fpsOffset;
          y = fpsOffset;
          break;

        case LOWER_RIGHT:
          x = drawable.getWidth() - fpsWidth - fpsOffset;
          y = fpsOffset;
          break;
      }

      //Textλ��
      renderer.setColor(0.5f,1f,0.7f,0.8f);
      renderer.draw(Text, x-670, y+510);

      renderer.draw(Intextup, 625,35);
      renderer.draw(Intextdown, 625, 10);
      
      renderer.draw("��", 401, 290);
      renderer.draw("����X:"+bulletX, 658, 440);
      renderer.draw("����Y:"+bulletY, 658, 410);
      renderer.draw("����Z:"+bulletZ, 658, 380);
      
      renderer.draw(XJG, 670, 330);
      
      renderer.endRendering();
      
      
      
    }

  }
  
  //
  public void drawclean() {
    
	  renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
      //
      if(inforYN>0){
    	  renderer.draw(Strinfor, 150, 330);
    	  renderer.setColor(Color.green);
    	  inforYN--;
      }
      renderer.endRendering();

  }

  private void recomputeFPSSize(float fps) {
    String fpsText;
    int fpsMagnitude;
    if (fps >= 10000) {
      fpsText = "10000.00";
      fpsMagnitude = 5;
    } else if (fps >= 1000) {
      fpsText = "1000.00";
      fpsMagnitude = 4;
    } else if (fps >= 100) {
      fpsText = "100.00";
      fpsMagnitude = 3;
    } else if (fps >= 10) {
      fpsText = "10.00";
      fpsMagnitude = 2;
    } else {
      fpsText = "9.00";
      fpsMagnitude = 1;
    }

    if (fpsMagnitude > this.fpsMagnitude) {
      Rectangle2D bounds = renderer.getBounds("FPS: " + fpsText);
      fpsWidth = (int) bounds.getWidth();
      fpsHeight = (int) bounds.getHeight();
      fpsOffset = (int) (fpsHeight * 0.5f);
      this.fpsMagnitude = fpsMagnitude;
    }
  }
}
