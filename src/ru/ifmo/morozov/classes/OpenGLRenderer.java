package ru.ifmo.morozov.classes;

import ru.ifmo.morozov.enums.CheckerType;
import ru.ifmo.morozov.enums.Colour;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import ru.ifmo.morozov.interfaces.Observer;


/**
 * Created by vks on 3/3/15.
 */
public class OpenGLRenderer implements GLEventListener, Observer {

    private static final GLU glu = new GLU();
    private Field field;
    private float width;
    private float ang = 0;

    private Model board;
    private Model checker;
    private Model selector;
    private Texture whiteTex;
    private Texture blackTex;
    private Texture boardTex;
    private Texture selectorTex;

    private BufferedModel bufferedBoard;
    private BufferedModel bufferedWhiteChecker;
    private BufferedModel bufferedBlackChecker;
    private BufferedModel bufferedSelector;


    public OpenGLRenderer(Field field, String root) {
        this.field = field;
        String boardFile = root + "mdl/model.board";
        String checkerFile = root + "mdl/model.checker";
        String selectorFile = root + "mdl/model.selector";
        String blackTextureFile = root + "tex/texture.black";
        String whiteTextureFile = root + "tex/texture.white";
        String boardTextureFile = root + "tex/texture.board";
        String selectorTextureFile = root + "tex/texture.selector";

        board = new Model(boardFile);
        checker = new Model(checkerFile);
        selector = new Model(selectorFile);
        whiteTex = new Texture(whiteTextureFile);
        blackTex = new Texture(blackTextureFile);
        boardTex = new Texture(boardTextureFile);
        selectorTex = new Texture(selectorTextureFile);

        bufferedBoard = new BufferedModel();
        bufferedWhiteChecker = new BufferedModel();
        bufferedBlackChecker = new BufferedModel();
        bufferedSelector = new BufferedModel();

    }

    private void render(GLAutoDrawable drawable, BufferedModel model, int indices) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, model.vertexBuffer);
        gl.glVertexPointer(3, GL.GL_FLOAT, 0, 0);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, model.normalBuffer);
        gl.glNormalPointer(GL.GL_FLOAT, 0, 0);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, model.textureCoordBuffer);
        gl.glTexCoordPointer(2, GL.GL_FLOAT, 0, 0);
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, model.indexBuffer);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, model.texture);


        gl.glDrawElements(GL2.GL_TRIANGLES, indices, GL2.GL_UNSIGNED_SHORT, 0);

        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -1.5f);
        gl.glRotatef(-45, 1, 0, 0);
        gl.glRotatef(180, 0, 0, 1);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);

        gl.glColor3f(1, 1, 1);


        gl.glPushMatrix();
        gl.glTranslatef(0.42f - (float) field.getPointer().x * 0.12f, -0.42f + (float) field.getPointer().y * 0.12f, 0f);
        if (!field.isChecked()) {
            gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_POINT);
        }
        render(drawable, bufferedSelector, selector.getIndices() * 3);
        gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_FILL);
        gl.glPopMatrix();

        render(drawable, bufferedBoard, board.getIndices() * 3);


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (field.getMatrix()[i][j] != null) {
                    gl.glPushMatrix();
                    if (field.getMatrix()[i][j].getType() == CheckerType.Simple) {
                        gl.glRotatef(180, 1, 0, 0);
                    }
                    gl.glTranslatef(-0.41f + (float) i * 0.12f, 0.44f - (float) j * 0.12f, -0.135f);
                   /* if (field.isChecked() && (i == field.getPointer().x) && (j == field.getPointer().y)) {
                        gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_POINT);
                    } */
                    if (field.getMatrix()[i][j].getColour() == Colour.White) {
                        render(drawable, bufferedWhiteChecker, checker.getIndices() * 3);
                    } else {
                        render(drawable, bufferedBlackChecker, checker.getIndices() * 3);
                    }
                    gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_FILL);
                    gl.glPopMatrix();
                }
            }
        }

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

        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);

        /* Creating VBO vertex buffer for board and loading model and texture into VRAM */

        int[] buffer = new int[4];
        gl.glGenBuffers(4, buffer, 0);
        bufferedBoard.vertexBuffer = buffer[0];
        bufferedBoard.normalBuffer = buffer[1];
        bufferedBoard.textureCoordBuffer = buffer[2];
        bufferedBoard.indexBuffer = buffer[3];

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

        int[] tId = new int[1];
        gl.glGenTextures(1, tId, 0);
        bufferedBoard.texture = tId[0];
        gl.glBindTexture(GL2.GL_TEXTURE_2D, bufferedBoard.texture);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, 3, boardTex.getWidth(),
                boardTex.getHeight(), 0, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE,
                boardTex.getImage());
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);

        /* Creating buffer and loading white checker */

        gl.glGenBuffers(4, buffer, 0);
        bufferedWhiteChecker.vertexBuffer = buffer[0];
        bufferedWhiteChecker.normalBuffer = buffer[1];
        bufferedWhiteChecker.textureCoordBuffer = buffer[2];
        bufferedWhiteChecker.indexBuffer = buffer[3];

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferedWhiteChecker.vertexBuffer);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, checker.getVertices() * 12, checker.getVertexArray(), GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferedWhiteChecker.normalBuffer);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, checker.getVertices() * 12, checker.getNormalArray(), GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferedWhiteChecker.textureCoordBuffer);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, checker.getVertices() * 8, checker.getTextureCoordArray(), GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);

        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, bufferedWhiteChecker.indexBuffer);
        gl.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, checker.getIndices() * 6, checker.getIndexArray(), GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);


        gl.glGenTextures(1, tId, 0);
        bufferedWhiteChecker.texture = tId[0];
        gl.glBindTexture(GL2.GL_TEXTURE_2D, bufferedWhiteChecker.texture);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, 3, whiteTex.getWidth(),
                whiteTex.getHeight(), 0, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE,
                whiteTex.getImage());
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);

        /* Copying geometry from white checker and loading texture for black checker */

        bufferedBlackChecker.indexBuffer = bufferedWhiteChecker.indexBuffer;
        bufferedBlackChecker.vertexBuffer = bufferedWhiteChecker.vertexBuffer;
        bufferedBlackChecker.normalBuffer = bufferedWhiteChecker.normalBuffer;
        bufferedBlackChecker.textureCoordBuffer = bufferedWhiteChecker.textureCoordBuffer;

        gl.glGenTextures(1, tId, 0);
        bufferedBlackChecker.texture = tId[0];
        gl.glBindTexture(GL2.GL_TEXTURE_2D, bufferedBlackChecker.texture);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, 3, blackTex.getWidth(),
                blackTex.getHeight(), 0, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE,
                blackTex.getImage());
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);

         /* Creating buffer and loading selector */

        gl.glGenBuffers(4, buffer, 0);
        bufferedSelector.vertexBuffer = buffer[0];
        bufferedSelector.normalBuffer = buffer[1];
        bufferedSelector.textureCoordBuffer = buffer[2];
        bufferedSelector.indexBuffer = buffer[3];

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferedSelector.vertexBuffer);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, selector.getVertices() * 12, selector.getVertexArray(), GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferedSelector.normalBuffer);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, selector.getVertices() * 12, selector.getNormalArray(), GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferedSelector.textureCoordBuffer);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, selector.getVertices() * 8, selector.getTextureCoordArray(), GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);

        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, bufferedSelector.indexBuffer);
        gl.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, selector.getIndices() * 6, selector.getIndexArray(), GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);


        gl.glGenTextures(1, tId, 0);
        bufferedSelector.texture = tId[0];
        gl.glBindTexture(GL2.GL_TEXTURE_2D, bufferedSelector.texture);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, 3, selectorTex.getWidth(),
                selectorTex.getHeight(), 0, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE,
                selectorTex.getImage());
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);

    }

    public void update() {

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
