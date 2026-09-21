package com.example.util

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.R
import java.util.Locale

/**
 * Supported app languages for Computer Master.
 */
enum class AppLanguage(
  val code: String,
  val displayName: String,
  val nativeName: String,
  val symbol: String
) {
  BENGALI("bn", "Bengali", "বাংলা", "বাং"),
  ENGLISH("en", "English", "English", "EN"),
  HINDI("hi", "Hindi", "हिन्दी", "हिं");

  companion object {
    fun fromCode(code: String): AppLanguage =
      entries.find { it.code.equals(code, ignoreCase = true) } ?: ENGLISH
  }
}

val LocalAppLanguage = staticCompositionLocalOf { AppLanguage.ENGLISH }

/**
 * Creates a localized Context configured for the specified language.
 */
fun Context.createLocalizedContext(language: AppLanguage): Context {
  val locale = Locale(language.code)
  Locale.setDefault(locale)
  val config = Configuration(resources.configuration)
  config.setLocale(locale)
  return createConfigurationContext(config)
}

/**
 * Composition provider that injects both the LocalAppLanguage and the
 * localized Context so that standard Compose [stringResource] calls automatically
 * resolve from the matching values, values-bn, or values-hi XML string catalogs.
 */
@Composable
fun ProvideAppLanguage(
  language: AppLanguage,
  content: @Composable () -> Unit
) {
  val currentContext = LocalContext.current
  val localizedContext = androidx.compose.runtime.remember(language, currentContext) {
    currentContext.createLocalizedContext(language)
  }
  val localizedConfig = androidx.compose.runtime.remember(language) {
    Configuration(currentContext.resources.configuration).apply {
      setLocale(Locale(language.code))
    }
  }

  CompositionLocalProvider(
    LocalAppLanguage provides language,
    LocalContext provides localizedContext,
    LocalConfiguration provides localizedConfig
  ) {
    content()
  }
}

/**
 * Convenient type-safe accessor for reactive localized strings.
 * Guarantees zero latency and instantaneous recomposition across all screens.
 */
object AppStrings {

  fun welcomeTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "কম্পিউটার মাস্টারে স্বাগতম"
    AppLanguage.ENGLISH -> "Welcome to Computer Master"
    AppLanguage.HINDI -> "कंप्यूटर मास्टर में आपका स्वागत है"
  }

  fun welcomeCreatedBy(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "নির্মাতা: Soumen Mondal"
    AppLanguage.ENGLISH -> "Created by Soumen Mondal"
    AppLanguage.HINDI -> "निर्माता: Soumen Mondal"
  }

  fun welcomeSubtitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "কম্পিউটিংয়ের মূল ভিত্তি, সিস্টেম আর্কিটেকচার, আইটি ইঞ্জিনিয়ারিং এবং ব্যবহারিক প্রযুক্তি দক্ষতায় দক্ষতা অর্জনের সম্পূর্ণ প্ল্যাটফর্ম।"
    AppLanguage.ENGLISH -> "Your comprehensive pathway to mastering computing fundamentals, system architecture, IT engineering, and practical technology skills."
    AppLanguage.HINDI -> "कंप्यूटिंग की बुनियादी बातें, सिस्टम आर्किटेक्चर, आईटी इंजीनियरिंग और व्यावहारिक तकनीकी कौशल में महारत हासिल करने का आपका संपूर्ण मंच।"
  }

  fun welcomeFeature1Title(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "২০টি পূর্ণাঙ্গ কোর্স"
    AppLanguage.ENGLISH -> "20 Comprehensive Courses"
    AppLanguage.HINDI -> "20 संपूर्ण पाठ्यक्रम"
  }

  fun welcomeFeature1Desc(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "শিক্ষানবিস পর্যায় থেকে নেটওয়ার্কিং, সাইবার নিরাপত্তা ও কৃত্রিম বুদ্ধিমত্তা"
    AppLanguage.ENGLISH -> "From beginner fundamentals to networking, security & AI"
    AppLanguage.HINDI -> "शुरुआती बुनियादी बातों से लेकर नेटवर्किंग, साइबर सुरक्षा और एआई तक"
  }

  fun welcomeFeature2Title(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ইন্টারেক্টিভ নলেজ কুইজ"
    AppLanguage.ENGLISH -> "Interactive Knowledge Quizzes"
    AppLanguage.HINDI -> "इंटरैक्टिव ज्ञान प्रश्नोत्तरी"
  }

  fun welcomeFeature2Desc(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "৫০টি পাঠ্য কুইজ, চূড়ান্ত সনদ পরীক্ষা ও দৈনিক অনুশীলন"
    AppLanguage.ENGLISH -> "50 lesson quizzes, final certifications & daily checks"
    AppLanguage.HINDI -> "50 पाठ प्रश्नोत्तरी, अंतिम प्रमाणन और दैनिक अभ्यास"
  }

  fun welcomeFeature3Title(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "অফলাইন অগ্রগতি ও নোট"
    AppLanguage.ENGLISH -> "Offline Progress & Study Notes"
    AppLanguage.HINDI -> "ऑफ़लाइन प्रगति और अध्ययन नोट्स"
  }

  fun welcomeFeature3Desc(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "এক্সপি, স্ট্রিক এবং বুকমার্ক ট্র্যাক করুন যে কোনো স্থানে"
    AppLanguage.ENGLISH -> "Track XP, streaks, bookmarks & retain knowledge anywhere"
    AppLanguage.HINDI -> "एक्सपी, स्ट्रीक और बुकमार्क कभी भी और कहीं भी ट्रैक करें"
  }

  fun welcomeBadgeFree(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "১০০% সম্পূর্ণ বিনামূল্যের ও অফলাইন লার্নিং প্ল্যাটফর্ম"
    AppLanguage.ENGLISH -> "100% Free & Offline-Ready Learning Platform"
    AppLanguage.HINDI -> "100% निःशुल्क और ऑफ़लाइन शिक्षण मंच"
  }

  fun getStarted(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "শুরু করুন"
    AppLanguage.ENGLISH -> "Get Started"
    AppLanguage.HINDI -> "शुरू करें"
  }

  fun navHome(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "হোম"
    AppLanguage.ENGLISH -> "Home"
    AppLanguage.HINDI -> "होम"
  }

  fun navCourses(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "কোর্স"
    AppLanguage.ENGLISH -> "Courses"
    AppLanguage.HINDI -> "कोर्स"
  }

  fun navQuiz(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "কুইজ"
    AppLanguage.ENGLISH -> "Quiz"
    AppLanguage.HINDI -> "क्विज़"
  }

  fun navProgress(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "অগ্রগতি"
    AppLanguage.ENGLISH -> "Progress"
    AppLanguage.HINDI -> "प्रगति"
  }

  fun navProfile(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "প্রোফাইল"
    AppLanguage.ENGLISH -> "Profile"
    AppLanguage.HINDI -> "प्रोफ़ाइल"
  }

  fun settings(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সেটিংস"
    AppLanguage.ENGLISH -> "Settings"
    AppLanguage.HINDI -> "सेटिंग्स"
  }

  fun appLanguage(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "অ্যাপের ভাষা"
    AppLanguage.ENGLISH -> "App Language"
    AppLanguage.HINDI -> "ऐप की भाषा"
  }

  fun chooseLanguage(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ভাষা নির্বাচন করুন"
    AppLanguage.ENGLISH -> "Choose Language"
    AppLanguage.HINDI -> "भाषा चुनें"
  }

  fun dailyReminder(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "দৈনিক অনুশীলনের অনুস্মারক"
    AppLanguage.ENGLISH -> "Daily Learning Reminder"
    AppLanguage.HINDI -> "दैनिक सीखने का अनुस्मारक"
  }

  fun dailyReminderDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "স্ট্রিক বজায় রাখতে রাত ৮:০০টায় নোটিফিকেশন পান"
    AppLanguage.ENGLISH -> "Notify at 8:00 PM to maintain streak"
    AppLanguage.HINDI -> "स्ट्रीक बनाए रखने के लिए रात 8:00 बजे सूचना प्राप्त करें"
  }

  fun hapticFeedback(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "হ্যাপটিক প্রতিক্রিয়া"
    AppLanguage.ENGLISH -> "Haptic Feedback"
    AppLanguage.HINDI -> "हैप्टिक फीडबैक"
  }

  fun hapticFeedbackDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সঠিক উত্তরের সময় হালকা কম্পন"
    AppLanguage.ENGLISH -> "Vibrate gently on correct quiz answer"
    AppLanguage.HINDI -> "सही उत्तर पर हल्का कंपन"
  }

  fun resetProgress(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "শেখার অগ্রগতি রিসেট করুন"
    AppLanguage.ENGLISH -> "Reset Learning State"
    AppLanguage.HINDI -> "सीखने की स्थिति रीसेट करें"
  }

  fun searchPlaceholder(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "২০টি কোর্স, বিষয় ও পাঠ খুঁজুন..."
    AppLanguage.ENGLISH -> "Search 20 courses, topics, lessons..."
    AppLanguage.HINDI -> "20 पाठ्यक्रम, विषय और पाठ खोजें..."
  }

  fun filterAll(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সকল"
    AppLanguage.ENGLISH -> "All"
    AppLanguage.HINDI -> "सभी"
  }

  fun filterBeginner(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "শিক্ষানবিস"
    AppLanguage.ENGLISH -> "Beginner"
    AppLanguage.HINDI -> "शुरुआती"
  }

  fun filterIntermediate(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "মধ্যবর্তী"
    AppLanguage.ENGLISH -> "Intermediate"
    AppLanguage.HINDI -> "मध्यवर्ती"
  }

  fun filterAdvanced(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "উন্নত"
    AppLanguage.ENGLISH -> "Advanced"
    AppLanguage.HINDI -> "उन्नत"
  }

  fun continueLearning(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "শেখা চালিয়ে যান"
    AppLanguage.ENGLISH -> "Continue Learning"
    AppLanguage.HINDI -> "सीखना जारी रखें"
  }

  fun startCourse(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "কোর্স শুরু করুন"
    AppLanguage.ENGLISH -> "Start Course"
    AppLanguage.HINDI -> "कोर्स शुरू करें"
  }

  fun courseLibrary(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "কোর্স লাইব্রেরি"
    AppLanguage.ENGLISH -> "Course Library"
    AppLanguage.HINDI -> "पाठ्यक्रम पुस्तकालय"
  }

  fun cancel(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "বাতিল"
    AppLanguage.ENGLISH -> "Cancel"
    AppLanguage.HINDI -> "रद्द करें"
  }

  fun save(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সংরক্ষণ"
    AppLanguage.ENGLISH -> "Save"
    AppLanguage.HINDI -> "सहेजें"
  }

  fun done(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সম্পন্ন"
    AppLanguage.ENGLISH -> "Done"
    AppLanguage.HINDI -> "पूर्ण"
  }

  fun updateAvailableDialogTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "নতুন আপডেট উপলব্ধ!"
    AppLanguage.ENGLISH -> "New Update Available!"
    AppLanguage.HINDI -> "नया अपडेट उपलब्ध है!"
  }

  fun updateNewVersionBadge(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "নতুন সংস্করণ"
    AppLanguage.ENGLISH -> "New Version"
    AppLanguage.HINDI -> "नया संस्करण"
  }

  fun updateCurrentVersionBadge(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "বর্তমান"
    AppLanguage.ENGLISH -> "Current"
    AppLanguage.HINDI -> "वर्तमान"
  }

  fun updateWhatsNew(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "এই আপডেটে যা নতুন রয়েছে:"
    AppLanguage.ENGLISH -> "What's New in this Update:"
    AppLanguage.HINDI -> "इस अपडेट में क्या नया है:"
  }

  fun updateNow(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "এখনই আপডেট করুন"
    AppLanguage.ENGLISH -> "Update Now"
    AppLanguage.HINDI -> "अभी अपडेट करें"
  }

  fun updateLater(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "পরে মনে করিয়ে দিন"
    AppLanguage.ENGLISH -> "Later"
    AppLanguage.HINDI -> "बाद में"
  }

  fun updateDownloading(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "আপডেট এপিকে ডাউনলোড হচ্ছে..."
    AppLanguage.ENGLISH -> "Downloading update APK..."
    AppLanguage.HINDI -> "अपडेट एपीके डाउनलोड हो रहा है..."
  }

  fun updateReadyToInstall(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ডাউনলোড সম্পন্ন। প্যাকেজ ইনস্টলার খোলা হচ্ছে..."
    AppLanguage.ENGLISH -> "Download complete. Launching package installer..."
    AppLanguage.HINDI -> "डाउनलोड पूरा हुआ। पैकेज इंस्टालर शुरू हो रहा है..."
  }

  fun updateFailed(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "আপডেট ডাউনলোড ব্যর্থ হয়েছে। ইন্টারনেট সংযোগ পরীক্ষা করুন।"
    AppLanguage.ENGLISH -> "Update download failed. Please check internet connection."
    AppLanguage.HINDI -> "अपडेट डाउनलोड विफल रहा। कृपया इंटरनेट कनेक्शन जांचें।"
  }

  fun updateRetry(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "পুনরায় চেষ্টা করুন"
    AppLanguage.ENGLISH -> "Retry"
    AppLanguage.HINDI -> "पुनः प्रयास करें"
  }

  fun checkForUpdates(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "আপডেট পরীক্ষা করুন"
    AppLanguage.ENGLISH -> "Check for Updates"
    AppLanguage.HINDI -> "अपडेट जांचें"
  }

  fun checkForUpdatesDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "গিটহাবে কম্পিউটার মাস্টারের নতুন সংস্করণ এসেছে কিনা যাচাই করুন"
    AppLanguage.ENGLISH -> "Check if a newer version of Computer Master is available on GitHub"
    AppLanguage.HINDI -> "गिटहब पर कंप्यूटर मास्टर का नया संस्करण उपलब्ध है या नहीं जांचें"
  }

  fun checkingUpdates(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "আপডেট খোঁজা হচ্ছে..."
    AppLanguage.ENGLISH -> "Checking for updates..."
    AppLanguage.HINDI -> "अपडेट की जांच हो रही है..."
  }

  fun appUpToDate(lang: AppLanguage, version: String): String = when (lang) {
    AppLanguage.BENGALI -> "কম্পিউটার মাস্টার সম্পূর্ণ আপডেট রয়েছে ($version)"
    AppLanguage.ENGLISH -> "Computer Master is up to date ($version)"
    AppLanguage.HINDI -> "कंप्यूटर मास्टर पूरी तरह से अप-टू-डेट है ($version)"
  }

  fun updateSourceUrl(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "গিটহাব আপডেট ইউআরএল"
    AppLanguage.ENGLISH -> "GitHub Update URL"
    AppLanguage.HINDI -> "गिटहब अपडेट यूआरएल"
  }

  fun configureSource(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "কনফিগার"
    AppLanguage.ENGLISH -> "Configure"
    AppLanguage.HINDI -> "कॉन्फ़िगर"
  }

  fun resetDefaultUrl(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ডিফল্ট পুনরুদ্ধার করুন"
    AppLanguage.ENGLISH -> "Reset Default"
    AppLanguage.HINDI -> "डिफ़ॉल्ट रीसेट करें"
  }

  fun settingsTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সেটিংস ও আপডেট"
    AppLanguage.ENGLISH -> "Settings & Updates"
    AppLanguage.HINDI -> "सेटिंग्स और अपडेट"
  }
}
