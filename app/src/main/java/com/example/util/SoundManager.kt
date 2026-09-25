package com.example.util

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.SystemClock
import android.util.Log
import com.example.R
import java.util.concurrent.ConcurrentHashMap

/**
 * Clean, lightweight, professional sound effects manager for Computer Master.
 * Uses Android SoundPool for low-latency playback of royalty-free UI sounds.
 * Integrates with SharedPreferences to support sound effects ON/OFF setting.
 */
class SoundManager private constructor(context: Context) {

  private val appContext = context.applicationContext
  private val prefs = appContext.getSharedPreferences("computer_master_prefs", Context.MODE_PRIVATE)

  var isSoundEnabled: Boolean = prefs.getBoolean(KEY_SOUND_EFFECTS, true)
    private set

  private var soundPool: SoundPool? = null
  private val soundMap = ConcurrentHashMap<Int, Int>()
  private val loadedSounds = ConcurrentHashMap.newKeySet<Int>()
  private var lastPlayTime = 0L

  companion object {
    private const val TAG = "SoundManager"
    const val KEY_SOUND_EFFECTS = "sound_effects_enabled"

    @Volatile
    private var instance: SoundManager? = null

    fun getInstance(context: Context): SoundManager {
      return instance ?: synchronized(this) {
        instance ?: SoundManager(context).also { instance = it }
      }
    }
  }

  init {
    initSoundPool()
  }

  private fun initSoundPool() {
    try {
      val audioAttributes = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
        .build()

      val pool = SoundPool.Builder()
        .setMaxStreams(4)
        .setAudioAttributes(audioAttributes)
        .build()

      pool.setOnLoadCompleteListener { _, sampleId, status ->
        if (status == 0) {
          loadedSounds.add(sampleId)
        }
      }

      soundPool = pool
      loadSounds(pool)
    } catch (e: Throwable) {
      Log.w(TAG, "Failed to initialize SoundPool: ${e.message}")
    }
  }

  fun setSoundEnabled(enabled: Boolean) {
    isSoundEnabled = enabled
    prefs.edit().putBoolean(KEY_SOUND_EFFECTS, enabled).apply()
  }

  private fun loadSounds(pool: SoundPool) {
    try {
      soundMap[R.raw.sound_startup] = pool.load(appContext, R.raw.sound_startup, 1)
      soundMap[R.raw.sound_get_started] = pool.load(appContext, R.raw.sound_get_started, 1)
      soundMap[R.raw.sound_course_click] = pool.load(appContext, R.raw.sound_course_click, 1)
      soundMap[R.raw.sound_lesson_open] = pool.load(appContext, R.raw.sound_lesson_open, 1)
      soundMap[R.raw.sound_lesson_complete] = pool.load(appContext, R.raw.sound_lesson_complete, 1)
      soundMap[R.raw.sound_quiz_correct] = pool.load(appContext, R.raw.sound_quiz_correct, 1)
      soundMap[R.raw.sound_quiz_wrong] = pool.load(appContext, R.raw.sound_quiz_wrong, 1)
      soundMap[R.raw.sound_bookmark] = pool.load(appContext, R.raw.sound_bookmark, 1)
      soundMap[R.raw.sound_note_saved] = pool.load(appContext, R.raw.sound_note_saved, 1)
      soundMap[R.raw.sound_back] = pool.load(appContext, R.raw.sound_back, 1)
    } catch (e: Throwable) {
      Log.w(TAG, "Failed to load audio samples: ${e.message}")
    }
  }

  private fun play(resId: Int, volume: Float = 0.8f, minIntervalMs: Long = 40L) {
    if (!isSoundEnabled) return
    val pool = soundPool ?: return

    val now = SystemClock.uptimeMillis()
    if (now - lastPlayTime < minIntervalMs) return
    lastPlayTime = now

    val soundId = soundMap[resId] ?: return
    try {
      pool.play(soundId, volume, volume, 1, 0, 1.0f)
    } catch (e: Throwable) {
      Log.w(TAG, "Error playing sound: $resId (${e.message})")
    }
  }

  // 1. App opening: Very short soft premium startup sound (played once when app opens)
  fun playStartup() = play(R.raw.sound_startup, volume = 0.85f, minIntervalMs = 500L)

  // 2. GET STARTED button: Subtle soft click/tap sound
  fun playGetStarted() = play(R.raw.sound_get_started, volume = 0.80f)

  // 3. Course card: Short soft UI click sound when a course is opened
  fun playCourseClick() = play(R.raw.sound_course_click, volume = 0.80f)

  // 4. Lesson opened: Gentle educational transition sound
  fun playLessonOpen() = play(R.raw.sound_lesson_open, volume = 0.80f)

  // 5. Lesson completed: Pleasant short success/completion sound
  fun playLessonCompleted() = play(R.raw.sound_lesson_complete, volume = 0.85f)

  // 6. Quiz correct answer: Short positive success sound
  fun playQuizCorrect() = play(R.raw.sound_quiz_correct, volume = 0.85f)

  // 7. Quiz wrong answer: Soft error sound, not harsh or annoying
  fun playQuizWrong() = play(R.raw.sound_quiz_wrong, volume = 0.75f)

  // 8. Bookmark: Subtle confirmation sound when a lesson is bookmarked/unbookmarked
  fun playBookmark() = play(R.raw.sound_bookmark, volume = 0.80f)

  // 9. Notes saved: Short soft confirmation sound
  fun playNoteSaved() = play(R.raw.sound_note_saved, volume = 0.80f)

  // 10. Back button: Very subtle UI navigation sound
  fun playBack() = play(R.raw.sound_back, volume = 0.70f)

  fun release() {
    try {
      soundPool?.release()
      soundPool = null
      soundMap.clear()
      loadedSounds.clear()
    } catch (e: Throwable) {
      Log.w(TAG, "Error releasing sound pool: ${e.message}")
    }
  }
}
