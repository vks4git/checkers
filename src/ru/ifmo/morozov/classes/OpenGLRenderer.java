package ru.ifmo.morozov.classes;

import ru.ifmo.morozov.classes.Field;
import ru.ifmo.morozov.enums.Colour;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;


/**
 * Created by vks on 3/3/15.
 */
public class OpenGLRenderer implements GLEventListener {

    private static final GLU glu = new GLU();
    private Field field;
    private float width;
    private float ang = 0;

    private Model board;
    private Model checker;
    private Texture whiteTex;
    private Texture blackTex;
    private Texture boardTex;

    private BufferedModel bufferedBoard;
    private BufferedModel bufferedChecker;


    public OpenGLRenderer(Field field, String root) {
        this.field = field;
        String boardFile = root + "mdl/model.board";
        String checkerFile = root + "mdl/model.checker";
        String blackTextureFile = root + "tex/texture.black";
        String whiteTextureFile = root + "tex/texture.white";
        String boardTextureFile = root + "tex/texture.board";

        board = new Model(boardFile);
        checker = new Model(checkerFile);
        whiteTex = new Texture(whiteTextureFile);
        blackTex = new Texture(blackTextureFile);
        boardTex = new Texture(boardTextureFile);

        bufferedBoard = new BufferedModel();
        bufferedChecker = new BufferedModel();

    }

    public void display(GLAutoDrawable gLDrawable) {

        GL2 gl = gLDrawable.getGL().getGL2();
/*
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -1.5f);
        gl.glRotatef(ang, 0, 1, 0);
        ang += 0.5;

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);


        gl.glColor3f(1, 1, 1);

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferedBoard.vertexBuffer);
        gl.glVertexPointer(3, GL.GL_FLOAT, 0, 0);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferedBoard.normalBuffer);
        gl.glNormalPointer(GL.GL_FLOAT, 0, 0);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferedBoard.textureCoordBuffer);
        gl.glTexCoordPointer(2, GL.GL_FLOAT, 0, 0);
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, bufferedBoard.indexBuffer);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, bufferedBoard.texture);


        gl.glDrawElements(GL2.GL_TRIANGLES, board.getIndices(), GL2.GL_UNSIGNED_SHORT, 0);

        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0); */

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

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);


        int[] buffer = new int[4];
        gl.glGenBuffers(4, buffer, 0);
        bufferedBoard.vertexBuffer = buffer[0];
        bufferedBoard.normalBuffer = buffer[1];
        bufferedBoard.textureCoordBuffer = buffer[2];
        bufferedBoard.indexBuffer = buffer[3];

        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferedBoard.vertexBuffer);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, board.getVertices() * 12, board.getVertexArray(), GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferedBoard.normalBuffer);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, board.getVertices() * 12, board.getNormalArray(), GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferedBoard.textureCoordBuffer);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, board.getVertices() * 8, board.getTextureCoordArray(), GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);

        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, bufferedBoard.indexBuffer);
        gl.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, board.getIndices() * 6, board.getIndexArray(), GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);


        int [] tId = new int[1];
        gl.glGenTextures(1, tId, 0);
        bufferedBoard.texture = tId[0];
        gl.glBindTexture(GL2.GL_TEXTURE_2D, bufferedBoard.texture);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, 3, boardTex.getWidth(),
                boardTex.getHeight(), 0, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE,
                boardTex.getImage());

        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);

        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
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

    public void dispose(GLAutoDrawable glDrawable) {
        GL2 gl = glDrawable.getGL().getGL2();
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
    }

}
