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

  fun soundEffects(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সাউন্ড এফেক্টস"
    AppLanguage.ENGLISH -> "Sound Effects"
    AppLanguage.HINDI -> "ध्वनि प्रभाव"
  }

  fun soundEffectsDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "বোতাম, পাঠ এবং কুইজের জন্য অডিও প্রতিক্রিয়া"
    AppLanguage.ENGLISH -> "Audio feedback for buttons, lessons, and quizzes"
    AppLanguage.HINDI -> "बटन, पाठ और क्विज़ के लिए ऑडियो प्रतिक्रिया"
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

  fun latestVersionInstalled(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "আপনি সর্বশেষ সংস্করণটি ব্যবহার করছেন।"
    AppLanguage.ENGLISH -> "You are using the latest version."
    AppLanguage.HINDI -> "आप नवीनतम संस्करण का उपयोग कर रहे हैं।"
  }

  fun updateAvailableTitle(lang: AppLanguage, version: String): String = when (lang) {
    AppLanguage.BENGALI -> "নতুন সংস্করণ $version উপলব্ধ!"
    AppLanguage.ENGLISH -> "Version $version Available"
    AppLanguage.HINDI -> "नया संस्करण $version उपलब्ध है!"
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
    AppLanguage.BENGALI -> "সেটিংস ও পছন্দসমূহ"
    AppLanguage.ENGLISH -> "Settings & Preferences"
    AppLanguage.HINDI -> "सेटिंग्स और प्राथमिकताएं"
  }

  // Section 1: Account
  fun settingsAccount(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "অ্যাকাউন্ট"
    AppLanguage.ENGLISH -> "Account"
    AppLanguage.HINDI -> "खाता"
  }

  fun login(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "লগইন"
    AppLanguage.ENGLISH -> "Log In"
    AppLanguage.HINDI -> "लॉग इन"
  }

  fun register(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "নিবন্ধন করুন"
    AppLanguage.ENGLISH -> "Register"
    AppLanguage.HINDI -> "रजिस्टर करें"
  }

  fun logout(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "লগআউট"
    AppLanguage.ENGLISH -> "Log Out"
    AppLanguage.HINDI -> "लॉग आउट"
  }

  fun guestModeTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "গেস্ট মোড (অতিথি শিক্ষার্থী)"
    AppLanguage.ENGLISH -> "Guest Learner Mode"
    AppLanguage.HINDI -> "अतिथि शिक्षार्थी मोड"
  }

  fun guestModeDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "স্থানীয় ডিভাইসে সমস্ত অগ্রগতি সংরক্ষিত হচ্ছে। ক্লাউড সিঙ্ক পরবর্তী ধাপে যুক্ত হবে।"
    AppLanguage.ENGLISH -> "All course progress is saved locally on this device. Cloud sync arriving in Step 2."
    AppLanguage.HINDI -> "सभी पाठ्यक्रम प्रगति इस डिवाइस पर स्थानीय रूप से सहेजी गई है। क्लाउड सिंक अगले चरण में आएगा।"
  }

  // Section 2: Language
  fun settingsLanguage(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ভাষা নির্বাচন"
    AppLanguage.ENGLISH -> "Language"
    AppLanguage.HINDI -> "भाषा चयन"
  }

  fun settingsLanguageDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "আপনার পছন্দের ভাষায় পাঠ্যক্রম ও ইন্টারফেস উপভোগ করুন"
    AppLanguage.ENGLISH -> "Choose your preferred language for courses and interface"
    AppLanguage.HINDI -> "पाठ्यक्रम और इंटरफ़ेस के लिए अपनी पसंदीदा भाषा चुनें"
  }

  // Section 3: Appearance
  fun settingsAppearance(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "থিম ও দৃশ্যমানতা"
    AppLanguage.ENGLISH -> "Appearance"
    AppLanguage.HINDI -> "दिखावट और थीम"
  }

  fun settingsAppearanceDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "অ্যাপের ইন্টারফেস থিম নির্বাচন করুন"
    AppLanguage.ENGLISH -> "Select your preferred visual display mode"
    AppLanguage.HINDI -> "अपना पसंदीदा डिस्प्ले मोड चुनें"
  }

  fun themeSystem(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সিস্টেম ডিফল্ট"
    AppLanguage.ENGLISH -> "System Default"
    AppLanguage.HINDI -> "सिस्टम डिफ़ॉल्ट"
  }

  fun themeLight(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "লাইট"
    AppLanguage.ENGLISH -> "Light"
    AppLanguage.HINDI -> "लाइट"
  }

  fun themeDark(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ডার্ক"
    AppLanguage.ENGLISH -> "Dark"
    AppLanguage.HINDI -> "डार्क"
  }

  // Section 4: Updates
  fun settingsUpdates(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "আপডেট ও সংস্করণ"
    AppLanguage.ENGLISH -> "Updates"
    AppLanguage.HINDI -> "अपडेट और संस्करण"
  }

  fun updateNotifications(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "আপডেট বিজ্ঞপ্তি"
    AppLanguage.ENGLISH -> "Update Notifications"
    AppLanguage.HINDI -> "अपडेट सूचनाएं"
  }

  fun updateNotificationsDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "নতুন সংস্করণ বা এপিকে প্রকাশিত হলে সতর্কতা পান"
    AppLanguage.ENGLISH -> "Receive alert notifications when newer releases are available"
    AppLanguage.HINDI -> "नया संस्करण उपलब्ध होने पर सूचना अलर्ट प्राप्त करें"
  }

  // Section 5: About
  fun settingsAbout(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "অ্যাপ পরিচিতি"
    AppLanguage.ENGLISH -> "About Computer Master"
    AppLanguage.HINDI -> "कंप्यूटर मास्टर के बारे में"
  }

  fun aboutCreator(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "নির্মাতা: Soumen Mondal"
    AppLanguage.ENGLISH -> "Created by Soumen Mondal"
    AppLanguage.HINDI -> "निर्माता: Soumen Mondal"
  }

  fun aboutDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "কম্পিউটার সায়েন্স, সিস্টেম আর্কিটেকচার, হার্ডওয়্যার ও প্রোগ্রামিং শেখার সর্বজনীন প্ল্যাটফর্ম।"
    AppLanguage.ENGLISH -> "A comprehensive educational suite for mastering computer science, systems architecture, hardware, and modern software skills."
    AppLanguage.HINDI -> "कंप्यूटर विज्ञान, सिस्टम आर्किटेक्चर, हार्डवेयर और सॉफ्टवेयर कौशल में महारत हासिल करने का संपूर्ण शैक्षिक मंच।"
  }

  // Section 6: Privacy
  fun settingsPrivacy(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "গোপনীয়তা ও শর্তাবলী"
    AppLanguage.ENGLISH -> "Privacy & Legal"
    AppLanguage.HINDI -> "गोपनीयता और शर्तें"
  }

  fun privacyPolicy(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "গোপনীয়তা নীতি"
    AppLanguage.ENGLISH -> "Privacy Policy"
    AppLanguage.HINDI -> "गोपनीयता नीति"
  }

  fun termsAndConditions(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ব্যবহারের শর্তাবলী"
    AppLanguage.ENGLISH -> "Terms & Conditions"
    AppLanguage.HINDI -> "नियम और शर्तें"
  }

  fun close(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "বন্ধ করুন"
    AppLanguage.ENGLISH -> "Close"
    AppLanguage.HINDI -> "बंद करें"
  }

  // Authentication & Security Strings
  fun authTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "লগইন / রেজিস্টার"
    AppLanguage.ENGLISH -> "Sign In / Register"
    AppLanguage.HINDI -> "साइन इन / रजिस्टर"
  }

  fun authSubtitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "কম্পিউটার মাস্টারে নিরাপদ ও সুরক্ষিত অ্যাক্সেসের জন্য যাচাই করুন"
    AppLanguage.ENGLISH -> "Verify your identity to access all courses, lessons, and tests safely"
    AppLanguage.HINDI -> "सभी पाठ्यक्रमों, पाठों और परीक्षणों तक सुरक्षित पहुंच के लिए पहचान सत्यापित करें"
  }

  fun authRegisterTab(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "রেজিস্টার"
    AppLanguage.ENGLISH -> "REGISTER"
    AppLanguage.HINDI -> "रजिस्टर"
  }

  fun authLoginTab(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "লগইন"
    AppLanguage.ENGLISH -> "LOGIN"
    AppLanguage.HINDI -> "लॉगिन"
  }

  fun authFullName(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "আপনার পুরো নাম"
    AppLanguage.ENGLISH -> "Full Name"
    AppLanguage.HINDI -> "पूरा नाम"
  }

  fun authPhoneOrEmail(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "মোবাইল নম্বর বা ইমেল"
    AppLanguage.ENGLISH -> "Phone Number or Email"
    AppLanguage.HINDI -> "मोबाइल नंबर या ईमेल"
  }

  fun authSendOtp(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ওটিপি পাঠান"
    AppLanguage.ENGLISH -> "SEND OTP"
    AppLanguage.HINDI -> "ओटीपी भेजें"
  }

  fun authOtpVerification(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ওটিপি যাচাইকরণ"
    AppLanguage.ENGLISH -> "OTP Verification"
    AppLanguage.HINDI -> "ओटीपी सत्यापन"
  }

  fun authEnterOtp(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "৬ ডিজিটের ওটিপি লিখুন"
    AppLanguage.ENGLISH -> "Enter 6-digit OTP code"
    AppLanguage.HINDI -> "6 अंकों का ओटीपी कोड दर्ज करें"
  }

  fun authVerifyAndContinue(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "যাচাই করুন ও প্রবেশ করুন"
    AppLanguage.ENGLISH -> "VERIFY & CONTINUE"
    AppLanguage.HINDI -> "सत्यापित करें और आगे बढ़ें"
  }

  fun authResendOtp(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "পুনরায় ওটিপি পাঠান"
    AppLanguage.ENGLISH -> "Resend OTP"
    AppLanguage.HINDI -> "ओटीपी पुनः भेजें"
  }

  fun authSecurityNote(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সুরক্ষিত যাচাইকরণ: কোনো ডামি ওটিপি বা পাসওয়ার্ড প্লেইন টেক্সটে সংরক্ষিত হয় না।"
    AppLanguage.ENGLISH -> "Real Verification: Tokens are securely managed. Passwords or OTPs are never stored in plain text."
    AppLanguage.HINDI -> "सुरक्षित सत्यापन: टोकन सुरक्षित रूप से प्रबंधित होते हैं। पासवर्ड या ओटीपी कभी भी सादे पाठ में संग्रहीत नहीं होते हैं।"
  }

  fun continueWithGoogle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "Google দিয়ে চালিয়ে যান"
    AppLanguage.ENGLISH -> "Continue with Google"
    AppLanguage.HINDI -> "Google के साथ जारी रखें"
  }

  fun signingInWithGoogle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "Google দিয়ে সাইন ইন হচ্ছে..."
    AppLanguage.ENGLISH -> "Signing in with Google..."
    AppLanguage.HINDI -> "Google के साथ साइन इन हो रहा है..."
  }

  fun googleAuthBenefit(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "Google অ্যাকাউন্ট দিয়ে একটি ট্যাপেই নিরাপদ সাইন ইন করুন"
    AppLanguage.ENGLISH -> "Sign in securely with one tap using your Google account"
    AppLanguage.HINDI -> "अपने Google खाते से एक टैप में सुरक्षित साइन इन करें"
  }

  // Internet Requirement Strings
  fun internetRequiredTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ইন্টারনেট সংযোগ আবশ্যক"
    AppLanguage.ENGLISH -> "Internet Connection Required"
    AppLanguage.HINDI -> "इंटरनेट कनेक्शन आवश्यक है"
  }

  fun internetRequiredMessage(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "কম্পিউটার মাস্টার ব্যবহার করার জন্য ইন্টারনেট সংযোগ প্রয়োজন।"
    AppLanguage.ENGLISH -> "Internet connection is required to use Computer Master."
    AppLanguage.HINDI -> "कंप्यूटर मास्टर का उपयोग करने के लिए इंटरनेट कनेक्शन आवश्यक है।"
  }

  fun internetRetryBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "পুনরায় চেষ্টা করুন"
    AppLanguage.ENGLISH -> "Retry Connection"
    AppLanguage.HINDI -> "पुनः प्रयास करें"
  }

  fun internetOfflineSubtext(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "কোনো সক্রিয় নেটওয়ার্ক পাওয়া যায়নি। অনুগ্রহ করে ওয়াই-ফাই বা মোবাইল ডেটা চেক করুন।"
    AppLanguage.ENGLISH -> "No active network detected. Please ensure Wi-Fi or mobile data is turned on."
    AppLanguage.HINDI -> "कोई सक्रिय नेटवर्क नहीं मिला। कृपया सुनिश्चित करें कि वाई-फाई या मोबाइल डेटा चालू है।"
  }
}
