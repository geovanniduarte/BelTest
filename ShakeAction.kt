class ShakeDetector : SensorEventListener {

    private var mListener: OnShakeListener? = null
    private var mShakeTime: Long = 0
    private var mShakeCount: Int = 0
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null

    constructor(context: Context) {
        mSensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager?
        mAccelerometer = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSensorManager?.registerListener(
            this, mAccelerometer,
            SensorManager.SENSOR_DELAY_UI, Handler()
        )
    }

    fun setOnShakeListener(listener: OnShakeListener) {
        this.mListener = listener
    }

    interface OnShakeListener {
        fun onShake(count: Int)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {

        if (mListener != null) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val gX = x / SensorManager.GRAVITY_EARTH
            val gY = y / SensorManager.GRAVITY_EARTH
            val gZ = z / SensorManager.GRAVITY_EARTH

            val gForce = Math.sqrt((gX * gX + gY * gY + gZ * gZ).toDouble()).toFloat()

            if (gForce > SHAKE_MAX_GRAVITY_ACEPTED) {
                val now = System.currentTimeMillis()
                if (mShakeTime + SHAKE_WAIT_NEW_MOVE > now) {
                    return
                }

                if (mShakeTime + SHAKE_RESTART_COUNTS < now) {
                    mShakeCount = 0
                }

                mShakeTime = now
                mShakeCount++

                if (mShakeCount % 2 == 0) {
                    mListener!!.onShake(mShakeCount)
                }
            }
        }
    }

    companion object {
        private val SHAKE_MAX_GRAVITY_ACEPTED = 2.7f
        private val SHAKE_WAIT_NEW_MOVE = 500
        private val SHAKE_RESTART_COUNTS = 3000
    }
}