package com.eakurnikov.common.ui.smarttoast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.eakurnikov.common.R;
import com.eakurnikov.common.util.ReflectKt;

import java.util.ArrayList;

/**
 * Created by eakurnikov on 2020-01-16
 */
public final class AutoQueToast extends BaseSmartToast {
    private static NotificationService sService;

    private final ToastNotification mTn;
    private View mNextView;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link android.app.Application}
     *                or {@link android.app.Activity} object.
     */
    public AutoQueToast(Context context) {
        super(context);
        mTn = new ToastNotification();
        mTn.mY = context.getResources().getDimensionPixelSize(R.dimen.toast_y_offset);
        mTn.mGravity = context.getResources().getInteger(R.integer.config_toastDefaultGravity);
    }

    /**
     * Show the view for the specified duration.
     */
    @Override
    public void show() {
        if (mNextView == null) {
            throw new RuntimeException("setView must have been called");
        }

        final NotificationService service = getService();
        final ToastNotification tn = mTn;

        tn.mNextView = mNextView;
        service.enqueueToast(tn, tn.mDuration);
    }

    /**
     * Close the view if it's showing, or don't show it if it isn't showing yet.
     * You do not normally have to call this.  Normally view will disappear on its own
     * after the appropriate duration.
     */
    @Override
    public void cancel() {
        mTn.hide();
        getService().cancelToast(mTn);
    }

    /**
     * Set the view to show.
     *
     * @see #getView
     */
    @Override
    public void setView(@NonNull final View view) {
        mNextView = view;
    }

    /**
     * Return the view.
     *
     * @see #setView
     */
    public View getView() {
        return mNextView;
    }

    @Override
    public void setDuration(int duration) {
        mTn.mDuration = DelayConverter.INSTANCE.convertDurationToMs(duration);
    }

    /**
     * Return the duration.
     *
     * @see #setDuration
     */
    public int getDuration() {
        return mTn.mDuration;
    }

    /**
     * Set the margins of the view.
     *
     * @param horizontalMargin The horizontal margin, in percentage of the
     *                         container width, between the container's edges and the notification
     * @param verticalMargin   The vertical margin, in percentage of the
     *                         container height, between the container's edges and the notification
     */
    void setMargin(float horizontalMargin, float verticalMargin) {
        mTn.mHorizontalMargin = horizontalMargin;
        mTn.mVerticalMargin = verticalMargin;
    }

    /**
     * Return the horizontal margin.
     */
    float getHorizontalMargin() {
        return mTn.mHorizontalMargin;
    }

    /**
     * Return the vertical margin.
     */
    float getVerticalMargin() {
        return mTn.mVerticalMargin;
    }

    /**
     * Set the location at which the notification should appear on the screen.
     *
     * @see Gravity
     * @see #getGravity
     */
    @Override
    public void setGravity(int gravity, int xOffset, int yOffset) {
        mTn.mGravity = gravity;
        mTn.mX = xOffset;
        mTn.mY = yOffset;
    }

    /**
     * Get the location at which the notification should appear on the screen.
     *
     * @see Gravity
     * @see #getGravity
     */
    int getGravity() {
        return mTn.mGravity;
    }

    /**
     * Make a standard toast that just contains a text view.
     *
     * @param context  The context to use.  Usually your {@link android.app.Application}
     *                 or {@link android.app.Activity} object.
     * @param text     The text to show.  Can be formatted text.
     * @param duration How long to display the message.
     */
    static AutoQueToast makeText(Context context, CharSequence text, int duration) {
        final AutoQueToast result = new AutoQueToast(context);

        LayoutInflater inflate =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("InflateParams") final View view = inflate.inflate(R.layout.transient_notification, null);

        final TextView textView = view.findViewById(R.id.message);
        textView.setText(text);

        result.mNextView = view;
        result.mTn.mDuration = duration;

        return result;
    }

    /**
     * Make a standard toast that just contains a text view with the text from a resource.
     *
     * @param context  The context to use.  Usually your {@link android.app.Application}
     *                 or {@link android.app.Activity} object.
     * @param resId    The resource id of the string resource to use.  Can be formatted text.
     * @param duration How long to display the message.
     * @throws Resources.NotFoundException if the resource can't be found.
     */
    static AutoQueToast makeText(
            final Context context,
            @StringRes int resId,
            int duration
    ) throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId), duration);
    }

    /**
     * Update the text in a Toast that was previously created using one of the makeText() methods.
     *
     * @param resId The new text for the Toast.
     */
    void setText(@StringRes int resId) {
        setText(getContext().getText(resId));
    }

    /**
     * Update the text in a Toast that was previously created using one of the makeText() methods.
     *
     * @param s The new text for the Toast.
     */
    void setText(CharSequence s) {
        if (mNextView == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }

        final TextView textView = mNextView.findViewById(R.id.message);

        if (textView == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }

        textView.setText(s);
    }

    @Override
    public void setCancelOnOutsideTouch(boolean cancelOnOutsideTouch) {
        mTn.mCancelOnOutsideTouch = cancelOnOutsideTouch;
    }

    private static NotificationService getService() {
        if (sService != null) {
            return sService;
        }
        sService = new NotificationService();
        return sService;
    }

    private static class NotificationService {
        private static final int MAX_NOTIFICATIONS = 50;
        private static final int MESSAGE_TIMEOUT = 1;

        private final ArrayList<ToastRecord> mToastQueue = new ArrayList<>();
        private final WorkerHandler mHandler = new WorkerHandler();

        void enqueueToast(Notification callback, int duration) {
            synchronized (mToastQueue) {
                final ToastRecord record;
                int index = indexOfToast(callback);

                // If it's already in the queue, we update it in place, we don't
                // move it to the end of the queue.
                if (index >= 0) {
                    record = mToastQueue.get(index);
                    record.update(duration);
                } else {
                    // Limit the number of toasts
                    int count = mToastQueue.size();
                    if (count >= MAX_NOTIFICATIONS) {
                        return;
                    }
                    record = new ToastRecord(callback, duration);
                    mToastQueue.add(record);
                    index = mToastQueue.size() - 1;
                }

                // If it's at index 0, it's the current toast.  It doesn't matter if it's
                // new or just been updated.  Call back and tell it to show itself.
                // If the callback fails, this will remove it from the list, so don't
                // assume that it's valid after this.
                if (index == 0) {
                    showNextToast();
                }
            }
        }

        private int indexOfToast(Notification callback) {
            final ArrayList<ToastRecord> list = mToastQueue;
            int len = list.size();
            for (int i = 0; i < len; i++) {
                final ToastRecord r = list.get(i);
                if (r.callback.equals(callback)) {
                    return i;
                }
            }
            return -1;
        }

        private void showNextToast() {
            final ToastRecord record = mToastQueue.get(0);
            if (record != null) {
                record.callback.show();
                scheduleTimeout(record);
            }
        }

        private void scheduleTimeout(ToastRecord r) {
            mHandler.removeCallbacksAndMessages(r);
            final Message m = Message.obtain(mHandler, MESSAGE_TIMEOUT, r);
            long delay = r.duration;
            mHandler.sendMessageDelayed(m, delay);
        }


        void cancelToast(Notification callback) {
            if (callback == null) {
                return;
            }

            synchronized (mToastQueue) {
                int index = indexOfToast(callback);
                if (index >= 0) {
                    cancelToast(index);
                }
            }
        }

        private void cancelToast(int index) {
            final ToastRecord record = mToastQueue.get(index);
            record.callback.hide();
            mToastQueue.remove(index);
            if (mToastQueue.size() > 0) {
                showNextToast();
            }
        }

        private void handleTimeout(ToastRecord record) {
            synchronized (mToastQueue) {
                int index = indexOfToast(record.callback);
                if (index >= 0) {
                    cancelToast(index);
                }
            }
        }

        @SuppressLint("HandlerLeak")
        private final class WorkerHandler extends Handler {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_TIMEOUT:
                        handleTimeout((ToastRecord) msg.obj);
                        break;
                    default:
                        break;
                }
            }
        }

        private static final class ToastRecord {
            public final Notification callback;
            public int duration;

            private ToastRecord(Notification callback, int duration) {
                this.callback = callback;
                this.duration = duration;
            }

            void update(int duration) {
                this.duration = duration;
            }
        }
    }

    private static final class ToastNotification implements Notification {
        final Runnable mHide = new Runnable() {
            @Override
            public void run() {
                handleHide();
                // Don't do this in handleHide() because it is also invoked by handleShow()
                mNextView = null;
            }
        };

        private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

        @SuppressLint("HandlerLeak")
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                handleShow();
            }
        };

        int mGravity;
        int mX;
        int mY;
        float mHorizontalMargin;
        float mVerticalMargin;

        View mView;
        View mNextView;
        int mDuration;
        boolean mCancelOnOutsideTouch;

        WindowManager mWindowManager;

        private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mCancelOnOutsideTouch) {
                    hide();
                }
                return false;
            }
        };

        private ToastNotification() {
            // XXX This should be changed to use a Dialog, with a Theme.Toast
            // defined that sets up the layout params appropriately.
            final WindowManager.LayoutParams params = mParams;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.TRANSLUCENT;
            params.windowAnimations = R.style.ToastWindowAnimations;
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
            params.setTitle("Toast");
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        }

        /**
         * schedule handleShow into the right thread
         */
        @Override
        public void show() {
            mHandler.obtainMessage(0).sendToTarget();
        }

        /**
         * schedule handleHide into the right thread
         */
        @Override
        public void hide() {
            mHandler.post(mHide);
        }

        void handleShow() {
            if (mView != mNextView) {
                // remove the old view if necessary
                handleHide();
                mView = mNextView;
                Context context = mView.getContext().getApplicationContext();
                String packageName = mView.getContext().getPackageName();

                if (context == null) {
                    context = mView.getContext();
                }

                mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

                // We can resolve the Gravity here by using the Locale for getting
                // the layout direction
                final Configuration config = mView.getContext().getResources().getConfiguration();
                int gravity = mGravity;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    gravity = Gravity.getAbsoluteGravity(mGravity, config.getLayoutDirection());
                }

                mParams.gravity = gravity;

                if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
                    mParams.horizontalWeight = 1.0f;
                }

                if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
                    mParams.verticalWeight = 1.0f;
                }

                mParams.x = mX;
                mParams.y = mY;
                mParams.verticalMargin = mVerticalMargin;
                mParams.horizontalMargin = mHorizontalMargin;
                mParams.packageName = packageName;
                //Timeout for close toast when main activity loses focus, not applicable for Android < 7.1.1.
                //For Android >=7.1.1 the maximum value will be set from android core as 3.5 sec.
                ReflectKt.setPrivateFieldJava(mParams, "hideTimeoutMilliseconds", mDuration);

                if (mCancelOnOutsideTouch) {
                    mView.setOnTouchListener(mOnTouchListener);
                }

                if (mView.getParent() != null) {
                    mWindowManager.removeView(mView);
                }

                mWindowManager.addView(mView, mParams);
            }
        }

        void handleHide() {
            if (mView != null) {
                // note: checking parent() just to make sure the view has
                // been added...  i have seen cases where we get here when
                // the view isn't yet added, so let's try not to crash.
                if (mView.getParent() != null) {
                    mWindowManager.removeViewImmediate(mView);
                }
                mView = null;
            }
        }
    }

    private interface Notification {
        void show();

        void hide();
    }
}