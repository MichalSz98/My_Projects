package com.example.Dijkstra_algorithm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mVertexBtn;
    private Button mEdgeBtn;
    private Button mDijkstraBtn;
    private Button mResultBtn;
    private Button mRestartBtn;
    private Button mDialogOkButton;
    private ImageView mImageView;

    private Canvas mDrawingCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;

    private int mDistance;
    private int[][] mGraph;

    private boolean[][] mBoolArray;
    private boolean[][] mtempBoolArray;

    private int mNumVertex = 0;
    private int mRadiusCircle = 50;
    private int mTempCounter = 0;
    private int mTempNum;
    private float mTempX;
    private float mTempY;

    private String mEdgeChecked;

    private List<Float> mVertexCenterXCoordinates;
    private List<Float> mVertexCenterYCoordinates;
    private List<Float> mDistancesXCoordinates;
    private List<Float> mDistancesYCoordinates;
    private List<Integer> mDistances;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the widgets reference from XML layout
        mVertexBtn = (Button) findViewById(R.id.drawVertexBtn);
        mEdgeBtn = (Button) findViewById(R.id.drawEdgeBtn);
        mDijkstraBtn = (Button) findViewById(R.id.dijkstraBtn);
        mResultBtn = (Button) findViewById(R.id.showResultsBtn);
        mRestartBtn = (Button) findViewById(R.id.restartBtn);
        mImageView = (ImageView) findViewById(R.id.drawingImageView);

        // Set text on buttons
        mVertexBtn.setText("Vertex");
        mEdgeBtn.setText("Edge");
        mDijkstraBtn.setText("Dijkstra");
        mResultBtn.setText("Result");
        mRestartBtn.setText("New");

        // Buttons enabling
        mEdgeBtn.setEnabled(false);
        mDijkstraBtn.setEnabled(false);
        mResultBtn.setEnabled(false);
        mImageView.setEnabled(false);

        Toast.makeText(getApplicationContext(), "Click vertex button to add vertices.", Toast.LENGTH_LONG).show();

        // Vertex button clicked
        mVertexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("myTag", "Vertex button clicked");
                mVertexBtn.setEnabled(false);
                mImageView.setEnabled(true);

                mBitmap = Bitmap.createBitmap(mImageView.getWidth(), mImageView.getHeight(), Bitmap.Config.ARGB_8888);
                mDrawingCanvas = new Canvas(mBitmap);

                mPaint = new Paint();

                mDrawingCanvas.drawColor(Color.DKGRAY);

                mImageView.setImageBitmap(mBitmap);

                mVertexCenterXCoordinates = new ArrayList<>();
                mVertexCenterYCoordinates = new ArrayList<>();

                mDistancesXCoordinates = new ArrayList<>();
                mDistancesYCoordinates = new ArrayList<>();

                mDistances = new ArrayList<>();
            }
        });

        // Drawing vertices
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float X = event.getX(), Y = event.getY();
                    Log.d("Touch coordinates", "X: " + String.valueOf(X) + " Y: " + String.valueOf(Y));

                    // Drawing vertices, collisions part
                    if (X > mRadiusCircle && X <  mImageView.getWidth() - mRadiusCircle && Y > mRadiusCircle && Y <  mImageView.getHeight() - mRadiusCircle)
                    {
                        if (mVertexCenterXCoordinates.size() == 0)
                        {
                            // Draw first vertex
                            drawInactiveVertex(X, Y, mNumVertex);

                            mVertexCenterXCoordinates.add(X);
                            mVertexCenterYCoordinates.add(Y);
                            mNumVertex++;
                        }
                        else
                        {
                            // To avoid collision with each other vertices
                            int counter = 0;
                            for (int i = 0; i < mVertexCenterXCoordinates.size(); i++)
                            {
                                if (X > mVertexCenterXCoordinates.get(i) + 2 * mRadiusCircle || X < mVertexCenterXCoordinates.get(i) - 2 * mRadiusCircle)
                                    counter++;
                                else if (Y > mVertexCenterYCoordinates.get(i) + 2 * mRadiusCircle || Y < mVertexCenterYCoordinates.get(i) - 2 * mRadiusCircle)
                                    counter++;
                            }

                            if (counter == mVertexCenterXCoordinates.size())
                            {
                                drawInactiveVertex(X, Y, mNumVertex);

                                mVertexCenterXCoordinates.add(X);
                                mVertexCenterYCoordinates.add(Y);
                                mNumVertex++;

                                if (!mEdgeBtn.isEnabled())
                                    mEdgeBtn.setEnabled(true);
                            }
                        }
                    }
                    mImageView.setImageBitmap(mBitmap);
                }
                return true;
            }
        });

        // Edge button clicked
        mEdgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("myTag", "Edge button clicked");

                mEdgeBtn.setEnabled(false);

                mGraph = new int[mNumVertex][mNumVertex];
                mBoolArray = new boolean[mNumVertex][mNumVertex];
                mtempBoolArray = new boolean[mNumVertex][mNumVertex];

                mImageView.setOnTouchListener(null);

                // Joining edges event
                mImageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            float X = event.getX(), Y = event.getY();

                            // Drawing edges part
                            for (int i = 0; i < mVertexCenterXCoordinates.size(); i++)
                            {
                                // Check if a vertex is clicked
                                if ((X <= mVertexCenterXCoordinates.get(i) + mRadiusCircle && X >= mVertexCenterXCoordinates.get(i) - mRadiusCircle)
                                        && (Y <= mVertexCenterYCoordinates.get(i) + mRadiusCircle && Y >= mVertexCenterYCoordinates.get(i) - mRadiusCircle))
                                {
                                    Log.d("myTag", "Vertex " + i + " clicked");

                                    if (mTempCounter == 0) {
                                        // Click on vertex

                                        mTempX = mVertexCenterXCoordinates.get(i);
                                        mTempY = mVertexCenterYCoordinates.get(i);
                                        mTempNum = i;

                                        drawActiveVertex(mTempX, mTempY, i);
                                        mImageView.setImageBitmap(mBitmap);

                                        mTempCounter++;
                                    }
                                    else if (mTempX != mVertexCenterXCoordinates.get(i) && mTempY != mVertexCenterYCoordinates.get(i) && !mBoolArray[mTempNum][i]) {
                                        // Drawing edges

                                        drawActiveVertex(mVertexCenterXCoordinates.get(i), mVertexCenterYCoordinates.get(i), i);
                                        mImageView.setImageBitmap(mBitmap);

                                        showInputTextDialog(i);

                                        mTempCounter = 0;
                                    }
                                    else if (mTempX == mVertexCenterXCoordinates.get(i) && mTempY == mVertexCenterYCoordinates.get(i))
                                    {
                                        // Inactice vertex after click on the same vertex

                                        drawInactiveVertex(mTempX, mTempY, i);
                                        mImageView.setImageBitmap(mBitmap);

                                        mTempCounter = 0;
                                    }
                                    else if (mBoolArray[mTempNum][i])
                                    {
                                        Toast.makeText(getApplicationContext(), "Edge between " + mTempNum + " and " + i + " vertices already exist.", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Log.d("TAG", "Other");
                                    }
                                }
                            }
                        }
                        return true;
                    }
                });
            }
        });

        // Dijkstra algorithm button
        mDijkstraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Dijkstra algorithm starts from 0 vertex.", Toast.LENGTH_LONG).show();

                mDijkstraBtn.setEnabled(false);
                mImageView.setEnabled(false);

                if (haveEdge(mGraph))
                {
                    mResultBtn.setEnabled(true);
                    Dijkstra dijkstra = new Dijkstra(mGraph);
                    dijkstra.doAlgorithm(0);

                    int[] prevVertices = dijkstra.getPrevVertices(), distances = dijkstra.getDistances();

                    drawPath(prevVertices, distances);
                    repaintVertices();
                    repaintDistances();

                    final String message = dijkstra.getMessage();

                    //Log.d("Message", message);

                    mResultBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showResults(message);
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Path does not exist from 0 vertex.", Toast.LENGTH_LONG).show();
                }

            }
        });

        // Restart imageView
        mRestartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mStartActivity = new Intent(MainActivity.this, MainActivity.class);
                int mPendingIntentId = 123456;

                PendingIntent mPendingIntent = PendingIntent.getActivity(MainActivity.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);

                System.exit(0);
            }
        });
    }

    private void drawArrowHead(Canvas canvas, float x0, float y0, float x1, float y1, Paint paint) {
        int arrowHeadLength = 20;
        int arrowHeadAngle = 45;
        float[] linePts = new float[] {x1 - arrowHeadLength, y1, x1, y1};
        float[] linePts2 = new float[] {x1, y1, x1, y1 + arrowHeadLength};
        Matrix rotateMat = new Matrix();

        // Get the center of the line
        float centerX = x1;
        float centerY = y1;

        // Set the angle
        double angle = Math.atan2(y1 - y0, x1 - x0) * 180 / Math.PI + arrowHeadAngle;

        // Rotate the matrix around the center
        rotateMat.setRotate((float) angle, centerX, centerY);
        rotateMat.mapPoints(linePts);
        rotateMat.mapPoints(linePts2);

        canvas.drawLine(linePts [0], linePts [1], linePts [2], linePts [3], paint);
        canvas.drawLine(linePts2 [0], linePts2 [1], linePts2 [2], linePts2 [3], paint);
    }

    // Set distances, a kind of edge
    @SuppressLint("ResourceType")
    private void showInputTextDialog(final int i) {
        mEdgeChecked = "Directed";

        final AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle("Edge")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).create();

        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Empty space
        layout.addView(new TextView(this));

        // Adding components to dialog
        // Set up radio buttons
        List<String> edgeOptions = new ArrayList<>();
        if (mBoolArray[mTempNum][i] == false && mBoolArray[i][mTempNum] == false) {
            edgeOptions.add("Directed");
            edgeOptions.add("Undirected");
        }
        else
            edgeOptions.add("Directed");

        final RadioGroup radioGroup = new RadioGroup(this);

        for (int y = 0; y < edgeOptions.size(); y++)
        {
            RadioButton radioButton= new RadioButton(this);
            radioButton.setText(edgeOptions.get(y));
            radioGroup.addView(radioButton);
        }

        // Set default checked
        radioGroup.check(1);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();

                for (int i = 0; i < childCount; i++){
                    RadioButton tempRbtn = (RadioButton) group.getChildAt(i);

                    if (tempRbtn.getId() == checkedId)
                        mEdgeChecked = tempRbtn.getText().toString();
                }
            }
        });

        layout.addView(radioGroup);

        // Empty space
        layout.addView(new TextView(this));

        // Set up SeekBar
        final SeekBar seekBar = new SeekBar(this);

        layout.addView(seekBar);

        // Empty space
        layout.addView(new TextView(this));

        // Set up the input
        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("Distance");
        layout.addView(input);

        // Set view
        builder.setView(layout);

        // Set up ok the button
        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

                mDialogOkButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_POSITIVE);

                input.setText("0");

                mDialogOkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDistance = Integer.parseInt(input.getText().toString());
                        if (mDistance > 0)
                        {
                            mDistances.add(mDistance);
                            int x1 = (int)mTempX;
                            int y1 = (int)mTempY;
                            int x2 = Math.round(mVertexCenterXCoordinates.get(i));
                            int y2 = Math.round(mVertexCenterYCoordinates.get(i));

                            if (mEdgeChecked.equals("Directed"))
                            {
                                drawDirectedEdge(x1, y1, x2, y2, 80);
                                mBoolArray[mTempNum][i] = true;

                                mtempBoolArray[mTempNum][i] = true;

                                completeAdjMatrixGraph(mTempNum, i, mDistance);
                            }
                            else if (mEdgeChecked.equals("Undirected"))
                            {
                                drawUndirectedEdge(x1, y1, x2, y2);
                                mBoolArray[mTempNum][i] = true;
                                mBoolArray[i][mTempNum] = true;

                                mtempBoolArray[mTempNum][i] = false;
                                mtempBoolArray[i][mTempNum] = false;

                                completeAdjMatrixGraph(mTempNum, i, mDistance);
                                completeAdjMatrixGraph(i, mTempNum, mDistance);
                            }

                            drawInactiveVertex(mTempX, mTempY, mTempNum);
                            drawInactiveVertex(mVertexCenterXCoordinates.get(i), mVertexCenterYCoordinates.get(i), i);

                            repaintDistances();
                            repaintVertices();

                            //showAdjMatrixGraph();

                            mImageView.setImageBitmap(mBitmap);

                            if (!mDijkstraBtn.isEnabled())
                                mDijkstraBtn.setEnabled(true);


                            dialog.dismiss();
                    }}
                });
            }
        });

        // Set distance validation
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String enteredString = s.toString();
                if (enteredString.startsWith("0") && enteredString.length() > 1) {
                    input.setText(enteredString.substring(1));
                }

                if (enteredString.length() == 0)
                    mDialogOkButton.setEnabled(false);
                else if (enteredString.length() > 0)
                    mDialogOkButton.setEnabled(true);

                if (enteredString.length() > 6) {
                    input.setText(enteredString.substring(0, enteredString.length() - 1));
                    Toast.makeText(getApplicationContext(), "Max distance value is 999999.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                input.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        builder.show();
    }

    private void drawUndirectedEdge(int x1, int y1, int x2, int y2)
    {
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5.0f);
        mPaint.setStyle(Paint.Style.STROKE);

        // 1. Draw line between vertices
        mDrawingCanvas.drawLine(x1, y1, x2, y2, mPaint);

        // 2. Draw distance between vertices
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(30);
        mPaint.setStyle(Paint.Style.FILL);

        mDrawingCanvas.drawText(String.valueOf(mDistance), (x1 + x2) / 2, (y1 + y2) / 2, mPaint);

        mDistancesXCoordinates.add((float) ((x1 + x2) / 2));
        mDistancesYCoordinates.add((float) ((y1 + y2) / 2));
    }

    private void drawDirectedEdge(int x1, int y1, int x2, int y2, int curveRadius)
    {
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5.0f);
        mPaint.setStyle(Paint.Style.STROKE);

        // 1. Draw curved line (directed edge)
        final Path path = new Path();
        int midX            = x1 + ((x2 - x1) / 2);
        int midY            = y1 + ((y2 - y1) / 2);
        float xDiff         = midX - x1;
        float yDiff         = midY - y1;
        double angle        = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) - 90;
        double angleRadians = Math.toRadians(angle);
        float pointX        = (float) (midX + curveRadius * Math.cos(angleRadians));
        float pointY        = (float) (midY + curveRadius * Math.sin(angleRadians));

        path.moveTo(x1, y1);
        path.cubicTo(x1, y1, pointX, pointY, x2, y2);

        mDrawingCanvas.drawPath(path, mPaint);

        // 2. Draw arrow head
        mPaint.setAntiAlias(false);
        mPaint.setStyle(Paint.Style.FILL);

        float centerPointLineX = (midX + pointX) / 2;
        float centerPointLineY = (midY + pointY) / 2;

        drawArrowHead(mDrawingCanvas, centerPointLineX, centerPointLineY , ((centerPointLineX + x2) / 2 + (pointX + x2) / 2) / 2, ((centerPointLineY + y2) / 2 + (pointY + y2) / 2) / 2, mPaint);

        // 3. Draw distance between vertices
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(30);

        mDrawingCanvas.drawText(String.valueOf(mDistance), centerPointLineX, centerPointLineY, mPaint);

        mDistancesXCoordinates.add(centerPointLineX);
        mDistancesYCoordinates.add(centerPointLineY);
    }

    private void drawActiveVertex(float x1, float y1, int vertexNumber)
    {
        // Draw active vertex
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStyle(Paint.Style.FILL);
        mDrawingCanvas.drawCircle(x1, y1, mRadiusCircle, mPaint);

        // Draw number of vertex
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(50);
        mDrawingCanvas.drawText(String.valueOf(vertexNumber), x1 - 15, y1 + 15, mPaint);

    }

    private void drawInactiveVertex(float x1, float y1, int vertexNumber)
    {
        // Draw inactive vertex
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL);
        mDrawingCanvas.drawCircle(x1, y1, mRadiusCircle, mPaint);

        // Draw number of vertex
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(50);
        mDrawingCanvas.drawText(String.valueOf(vertexNumber), x1 - 15, y1 + 15, mPaint);
    }

    private void repaintVertices()
    {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(50);

        for (int i = 0; i < mVertexCenterXCoordinates.size(); i++)
        {
            // Repaint vertex
            mPaint.setColor(Color.GRAY);
            mDrawingCanvas.drawCircle(mVertexCenterXCoordinates.get(i), mVertexCenterYCoordinates.get(i), mRadiusCircle, mPaint);
            mPaint.setColor(Color.YELLOW);

            // Repaint number of vertex
            mDrawingCanvas.drawText(String.valueOf(i), mVertexCenterXCoordinates.get(i) - 15, mVertexCenterYCoordinates.get(i) + 15, mPaint);
        }
    }

    // Result path
    private void drawPath(int[] prevVertex, int[] distances)
    {
        // Drawing edges
        mPaint.setStrokeWidth(7.0f);
        mPaint.setColor(Color.GREEN);

        for (int i = 1; i < mVertexCenterXCoordinates.size(); i++)
        {
            // Draw line between vertices
            if ((distances[i] < Integer.MAX_VALUE && distances[i] > 0) && !mtempBoolArray[prevVertex[i]][i])
            {
                mPaint.setAntiAlias(false);
                mPaint.setStyle(Paint.Style.FILL);
                mDrawingCanvas.drawLine(mVertexCenterXCoordinates.get(i), mVertexCenterYCoordinates.get(i), mVertexCenterXCoordinates.get(prevVertex[i]), mVertexCenterYCoordinates.get(prevVertex[i]), mPaint);
            }
            else if ((distances[i] < Integer.MAX_VALUE && distances[i] > 0) && mtempBoolArray[prevVertex[i]][i])
            {
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setAntiAlias(true);

                float x2 = mVertexCenterXCoordinates.get(i), y2 = mVertexCenterYCoordinates.get(i);
                float x1 = mVertexCenterXCoordinates.get(prevVertex[i]), y1 = mVertexCenterYCoordinates.get(prevVertex[i]);

                // 1. Draw curved line
                final Path path = new Path();
                int midX            = (int) (x1 + ((x2 - x1) / 2));
                int midY            = (int) (y1 + ((y2 - y1) / 2));
                float xDiff         = midX - x1;
                float yDiff         = midY - y1;
                double angle        = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) - 90;
                double angleRadians = Math.toRadians(angle);
                float pointX        = (float) (midX + 80 * Math.cos(angleRadians));
                float pointY        = (float) (midY + 80 * Math.sin(angleRadians));

                path.moveTo(x1, y1);
                path.cubicTo(x1, y1, pointX, pointY, x2, y2);

                mDrawingCanvas.drawPath(path, mPaint);

                // 2. Draw arrow head
                mPaint.setAntiAlias(false);
                mPaint.setStyle(Paint.Style.FILL);

                float centerPointLineX = (midX + pointX) / 2;
                float centerPointLineY = (midY + pointY) / 2;

                drawArrowHead(mDrawingCanvas, centerPointLineX, centerPointLineY , ((centerPointLineX + x2) / 2 + (pointX + x2) / 2) / 2, ((centerPointLineY + y2) / 2 + (pointY + y2) / 2) / 2, mPaint);
            }

        }

        repaintDistances();
        mImageView.setImageBitmap(mBitmap);
    }

    private void repaintDistances()
    {
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(30);

        for (int i = 0; i < mDistancesXCoordinates.size(); i++)
            mDrawingCanvas.drawText(String.valueOf(mDistances.get(i)), mDistancesXCoordinates.get(i) , mDistancesYCoordinates.get(i), mPaint);
    }

    // Checks that vertex 0 has at least 1 edge
    private boolean haveEdge(int[][] graph)
    {
        for (int i = 0; i < graph.length;i++)
            if (graph[0][i] > 0)
                return true;
        return false;
    }

    // Show results
    private void showResults(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Final results");
        builder.setMessage(message);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    private void completeAdjMatrixGraph(int fromVertex, int toVertex, int mDistance)
    {
        mGraph[fromVertex][toVertex] = mDistance;
    }

    private void showAdjMatrixGraph()
    {
        Log.d("Adjacency Matrix", Arrays.deepToString(mGraph));
    }
}
