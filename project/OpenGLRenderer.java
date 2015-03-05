import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 * Created by vks on 3/3/15.
 */
public class OpenGLRenderer implements GLEventListener {

    private static final GLU glu = new GLU();
    private Field field;
    private float width;

    public OpenGLRenderer(Field field) {
        this.field = field;
    }

    public void display(GLAutoDrawable gLDrawable) {
        GL2 gl = gLDrawable.getGL().getGL2();
        GLUquadric disk = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(disk, GLU.GLU_FILL);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -5.0f);

        float x;
        float y;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                gl.glBegin(GL.GL_TRIANGLE_STRIP);

                if ((i + j) % 2 == 0) {
                    gl.glColor3f(0.914f, 0.835f, 0.588f);
                } else {
                    gl.glColor3f(0.549f, 0.325f, 0.094f);
                }

                x = ((float) (i - 4)) / 2;
                y = ((float) (4 - j)) / 2;

                gl.glVertex2f(x, y);
                gl.glVertex2f(x, y - 0.5f);
                gl.glVertex2f(x + 0.5f, y);
                gl.glVertex2f(x + 0.5f, y - 0.5f);

                gl.glEnd();

                if (!(field.isChecked() && (i == field.getCheckCoords().x) && (j == field.getCheckCoords().y))) {

                    if (field.getMatrix()[i][j] != null) {

                        gl.glPushMatrix();
                        gl.glTranslatef(x + 0.25f, y - 0.25f, 0f);

                        if (field.getMatrix()[i][j].getColour() == Colour.Black) {
                            gl.glColor3f(0f, 0f, 0f);
                        } else {
                            gl.glColor3f(1f, 1f, 1f);
                        }

                        glu.gluDisk(disk, 0, 0.2, 32, 1);
                        gl.glPopMatrix();


                    }
                }
            }
        }

        x = ((float) (field.getPointer().x - 4)) / 2;
        y = ((float) (4 - field.getPointer().y)) / 2;

        if (field.isChecked()) {
            width = 0.05f;
            gl.glPushMatrix();
            gl.glTranslatef(x + 0.25f, y - 0.25f, 0f);

            if (field.getColour() == Colour.Black) {
                gl.glColor3f(0f, 0f, 0f);
            } else {
                gl.glColor3f(1f, 1f, 1f);
            }

            glu.gluDisk(disk, 0, 0.2, 32, 1);
            gl.glPopMatrix();
        } else {
            width = 0.03f;
        }

        gl.glColor3f(1f, 0f, 0f);

        gl.glBegin(GL.GL_TRIANGLE_STRIP);

        gl.glVertex2f(x + 0.5f, y);
        gl.glVertex2f(x, y);
        gl.glVertex2f(x + 0.5f, y - width);
        gl.glVertex2f(x, y - width);

        gl.glEnd();

        x += 0.5f;

        gl.glBegin(GL.GL_TRIANGLE_STRIP);

        gl.glVertex2f(x, y);
        gl.glVertex2f(x, y - 0.5f);
        gl.glVertex2f(x - width, y);
        gl.glVertex2f(x - width, y - 0.5f);

        gl.glEnd();

        y -= (0.5f - width);
        x -= 0.5f;

        gl.glBegin(GL.GL_TRIANGLE_STRIP);

        gl.glVertex2f(x + 0.5f, y);
        gl.glVertex2f(x, y);
        gl.glVertex2f(x + 0.5f, y - width);
        gl.glVertex2f(x, y - width);

        gl.glEnd();

        y += (0.5f - width);
        x += width;

        gl.glBegin(GL.GL_TRIANGLE_STRIP);

        gl.glVertex2f(x, y);
        gl.glVertex2f(x, y - 0.5f);
        gl.glVertex2f(x - width, y);
        gl.glVertex2f(x - width, y - 0.5f);

        gl.glEnd();

        glu.gluDeleteQuadric(disk);

    }


    public void init(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0.4f, 0.4f, 0.4f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
    }

    public void reshape(GLAutoDrawable gLDrawable, int x,
                        int y, int width, int height) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        if (height <= 0) {
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void dispose(GLAutoDrawable arg0) {

    }

}
