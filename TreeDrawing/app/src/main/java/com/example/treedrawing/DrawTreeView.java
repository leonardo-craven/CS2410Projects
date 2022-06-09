package com.example.treedrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DrawTreeView extends View {

    public DrawTreeView(Context context, int maxLength, int maxAngle) {
        super(context);
        paint.setColor(Color.rgb(92, 66, 29));
        paint.setStrokeWidth(10);
        this.maxLength = maxLength;
        this.maxAngle = maxAngle;
        setBackgroundColor(Color.rgb(63, 153, 242));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.tree = new Tree(getWidth() / 2, getHeight(), maxLength, maxAngle);
        super.onDraw(canvas);
        drawTree(tree.getRoot(), canvas);
    }

    private void drawTree(TreeNode node, Canvas canvas) {
        if (node == null) return;
        canvas.drawLine(node.startX, node.startY, node.endX, node.endY, paint);
        drawTree(node.left, canvas);
        drawTree(node.right, canvas);
    }

    private Paint paint = new Paint();
    private Tree tree;
    private int maxLength;
    private int maxAngle;
}
