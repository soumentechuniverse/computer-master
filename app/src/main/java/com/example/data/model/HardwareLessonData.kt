package com.example.data.model

import com.example.util.AppLanguage

enum class HardwareComponentType {
  MONITOR,
  CPU,
  KEYBOARD,
  MOUSE
}

data class HardwareComponentInfo(
  val type: HardwareComponentType,
  val nameEn: String,
  val nameBn: String,
  val nameHi: String,
  val categoryEn: String,
  val categoryBn: String,
  val categoryHi: String,
  val roleEn: String,
  val roleBn: String,
  val roleHi: String,
  val whatItDoesEn: String,
  val whatItDoesBn: String,
  val whatItDoesHi: String,
  val realWorldExampleEn: String,
  val realWorldExampleBn: String,
  val realWorldExampleHi: String,
  val funFactEn: String,
  val funFactBn: String,
  val funFactHi: String,
  val connectionPortEn: String,
  val connectionPortBn: String,
  val connectionPortHi: String
) {
  fun getName(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> nameBn
    AppLanguage.HINDI -> nameHi
    AppLanguage.ENGLISH -> nameEn
  }

  fun getCategory(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> categoryBn
    AppLanguage.HINDI -> categoryHi
    AppLanguage.ENGLISH -> categoryEn
  }

  fun getRole(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> roleBn
    AppLanguage.HINDI -> roleHi
    AppLanguage.ENGLISH -> roleEn
  }

  fun getWhatItDoes(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> whatItDoesBn
    AppLanguage.HINDI -> whatItDoesHi
    AppLanguage.ENGLISH -> whatItDoesEn
  }

  fun getRealWorldExample(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> realWorldExampleBn
    AppLanguage.HINDI -> realWorldExampleHi
    AppLanguage.ENGLISH -> realWorldExampleEn
  }

  fun getFunFact(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> funFactBn
    AppLanguage.HINDI -> funFactHi
    AppLanguage.ENGLISH -> funFactEn
  }

  fun getConnectionPort(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> connectionPortBn
    AppLanguage.HINDI -> connectionPortHi
    AppLanguage.ENGLISH -> connectionPortEn
  }
}

object HardwareLessonRepository {

  val components: List<HardwareComponentInfo> = listOf(
    // 1. MONITOR
    HardwareComponentInfo(
      type = HardwareComponentType.MONITOR,
      nameEn = "Monitor (Display Screen)",
      nameBn = "মনিটর (ডিসপ্লে স্ক্রিন)",
      nameHi = "मॉनिटर (डिस्प्ले स्क्रीन)",
      categoryEn = "Output Device (Visual)",
      categoryBn = "আউটপুট ডিভাইস (ভিজ্যুয়াল)",
      categoryHi = "आउटपुट डिवाइस (विज़ुअल)",
      roleEn = "The visual screen that presents text, images, videos, operating systems, and applications to your eyes.",
      roleBn = "ভিজ্যুয়াল স্ক্রিন যা লেখা, ছবি, ভিডিও, অপারেটিং সিস্টেম এবং সফটওয়্যার আমাদের চোখের সামনে প্রদর্শন করে।",
      roleHi = "विज़ुअल स्क्रीन जो टेक्स्ट, चित्र, वीडियो, ऑपरेटिंग सिस्टम और एप्लिकेशन को आपकी आंखों के सामने प्रदर्शित करती है।",
      whatItDoesEn = "Receives digital video signals from the graphics card and lights up millions of tiny color dots (pixels) 60 to 144 times every second to create smooth, visible moving pictures.",
      whatItDoesBn = "গ্রাফিক্স কার্ড থেকে ডিজিটাল ভিডিও সিগন্যাল গ্রহণ করে এবং প্রতি সেকেন্ডে ৬০ থেকে ১৪৪ বার লক্ষ লক্ষ ক্ষুদ্র রঙের বিন্দু (পিক্সেল) জ্বালিয়ে চলমান মসৃণ ছবি তৈরি করে।",
      whatItDoesHi = "ग्राफिक्स कार्ड से डिजिटल वीडियो सिग्नल प्राप्त करता है और गतिशील तस्वीरें बनाने के लिए प्रति सेकंड 60 से 144 बार लाखों छोटे रंगीन बिंदुओं (पिक्सेल) को रोशन करता है।",
      realWorldExampleEn = "Just like the television screen in your living room that lets you watch news and movies from your cable box, or your smartphone glass screen.",
      realWorldExampleBn = "আপনার ঘরের টেলিভিশনের মতো যা সেট-টপ বক্স থেকে সিনেমা ও খবর দেখায়, কিংবা আপনার স্মার্টফোনের কাঁচের ডিসপ্লে।",
      realWorldExampleHi = "आपके लिविंग रूम में टीवी स्क्रीन की तरह जो सेट-टॉप बॉक्स से फिल्में और समाचार दिखाती है, या आपके स्मार्टफोन की स्क्रीन।",
      funFactEn = "Standard Full HD monitors contain over 2,073,600 individual pixels (1920 × 1080), all controlled simultaneously!",
      funFactBn = "একটি সাধারণ ফুল-এইচডি মনিটরে ২০,৭৩,৬০০টিরও বেশি একক পিক্সেল থাকে (১৯২০ × ১০৮০), যা একসাথে নিয়ন্ত্রিত হয়!",
      funFactHi = "एक मानक फुल एचडी मॉनिटर में 2,073,600 से अधिक व्यक्तिगत पिक्सेल (1920 × 1080) होते हैं, जो एक साथ काम करते हैं!",
      connectionPortEn = "Connects to CPU via HDMI, DisplayPort, or USB-C cable.",
      connectionPortBn = "HDMI, DisplayPort বা USB-C ক্যাবল দিয়ে সিপিইউর সাথে সংযুক্ত হয়।",
      connectionPortHi = "HDMI, DisplayPort या USB-C केबल के माध्यम से सीपीयू से जुड़ता है।"
    ),

    // 2. CPU / SYSTEM UNIT
    HardwareComponentInfo(
      type = HardwareComponentType.CPU,
      nameEn = "CPU / System Unit (The Tower)",
      nameBn = "সিপিইউ / সিস্টেম ইউনিট (কম্পিউটার টাওয়ার)",
      nameHi = "सीपीयू / सिस्टम यूनिट (कंप्यूटर टॉवर)",
      categoryEn = "Processing & Control Hub",
      categoryBn = "প্রসেসিং ও নিয়ন্ত্রণ কেন্দ্র",
      categoryHi = "प्रोसेसिंग और नियंत्रण केंद्र",
      roleEn = "The primary metal case housing the computing engine: the Central Processing Unit (CPU), Motherboard, RAM memory, storage disk, and power supply.",
      roleBn = "কম্পিউটারের মূল ধাতব বাক্স যার ভেতরে মূল ইঞ্জিন থাকে: সেন্ট্রাল প্রসেসিং ইউনিট (সিপিইউ), মাদারবোর্ড, র‍্যাম মেমোরি, স্টোরেজ ড্রাইভ ও পাওয়ার সাপ্লাই।",
      roleHi = "कंप्यूटर का मुख्य धातु का डिब्बा जिसके अंदर मुख्य इंजन होता है: सेंट्रल प्रोसेसिंग यूनिट (सीपीयू), मदरबोर्ड, रैम मेमोरी, स्टोरेज और पावर सप्लाई।",
      whatItDoesEn = "Executes computer programs, performs billions of math calculations per second, coordinates all hardware peripherals, and keeps files safely saved in storage.",
      whatItDoesBn = "কম্পিউটার প্রোগ্রাম চালায়, প্রতি সেকেন্ডে শত কোটি গাণিতিক হিসাব করে, সমস্ত সংযুক্ত যন্ত্রপাতি সমন্বয় করে এবং তথ্য নিরাপদে সংরক্ষণ করে।",
      whatItDoesHi = "कंप्यूटर प्रोग्राम चलाता है, प्रति सेकंड अरबों गणनाएं करता है, सभी जुड़े उपकरणों का समन्वय करता है और फ़ाइलों को सुरक्षित रूप से सहेजता है।",
      realWorldExampleEn = "Like the human brain and heart combined: the brain thinks and calculates, while the heart pumps electricity to every connected limb.",
      realWorldExampleBn = "মানুষের মস্তিষ্ক ও হৃৎপিণ্ডের মেলবন্ধনের মতো: মস্তিষ্ক যেমন চিন্তা করে এবং হিসাব করে, আর হৃৎপিণ্ড পুরো শরীরে শক্তি যোগায়।",
      realWorldExampleHi = "मानव मस्तिष्क और हृदय के संयोजन की तरह: मस्तिष्क सोचता और गणना करता है, जबकि हृदय पूरे शरीर में शक्ति पहुंचाता है।",
      funFactEn = "The microscopic silicon CPU chip inside is about the size of a postage stamp, yet contains up to 20 billion microscopic transistors!",
      funFactBn = "ভেতরের সিলিকন সিপিইউ চিপটি একটি ডাকটিকিটের আকারের হলেও এতে ২,০০০ কোটিরও বেশি অতিক্ষুদ্র ট্রানজিস্টর থাকে!",
      funFactHi = "अंदर की सिलिकॉन सीपीयू चिप एक डाक टिकट के आकार की होती है, फिर भी इसमें 20 अरब से अधिक सूक्ष्म ट्रांजिस्टर होते हैं!",
      connectionPortEn = "Directly connected to power outlet and has USB, HDMI, Audio, and Ethernet ports.",
      connectionPortBn = "সরাসরি বিদ্যুৎ সকেটে যুক্ত হয় এবং এতে ইউএসবি, এইচডিএমআই ও নেটওয়ার্ক পোর্ট থাকে।",
      connectionPortHi = "सीधे बिजली से जुड़ता है और इसमें यूएसबी, एचडीएमआई और नेटवर्क पोर्ट होते हैं।"
    ),

    // 3. KEYBOARD
    HardwareComponentInfo(
      type = HardwareComponentType.KEYBOARD,
      nameEn = "Keyboard (Typing Input)",
      nameBn = "কীবোর্ড (টাইপিং ইনপুট)",
      nameHi = "कीबोर्ड (टाइपिंग इनपुट)",
      categoryEn = "Primary Input Device",
      categoryBn = "প্রধান ইনপুট ডিভাইস",
      categoryHi = "मुख्य इनपुट डिवाइस",
      roleEn = "The button board used to enter text, alphabet letters, numbers, and trigger operating system shortcut commands.",
      roleBn = "বোতাম সংবলিত ইনপুট বোর্ড যার মাধ্যমে লেখা, সংখ্যা, সংকেত এবং বিভিন্ন শর্টকাট কমান্ড কম্পিউটারে প্রদান করা হয়।",
      roleHi = "बटन वाला इनपुट बोर्ड जिसका उपयोग टेक्स्ट, अक्षर, संख्याएं दर्ज करने और कंप्यूटर को शॉर्टकट कमांड देने के लिए किया जाता है।",
      whatItDoesEn = "When you press a key, an internal electrical switch closes, instantly sending a specific binary scancode to the CPU to render that character.",
      whatItDoesBn = "যেকোনো কী চাপলে ভেতরের সুইচ বন্ধ হয়ে একটি সুনির্দিষ্ট বাইনারি কোড সিপিইউতে পাঠায়, যার ফলে স্ক্রিনে অক্ষরটি দেখা যায়।",
      whatItDoesHi = "जब आप कोई कुंजी दबाते हैं, तो एक आंतरिक विद्युत स्विच बंद हो जाता है और सीपीयू को एक विशिष्ट बाइनरी कोड भेजता है जिससे वह अक्षर स्क्रीन पर दिखता है।",
      realWorldExampleEn = "Like an electric typewriter or the on-screen keypad on your mobile phone used for sending text messages.",
      realWorldExampleBn = "একটি ইলেকট্রিক টাইপরাইটার অথবা আপনার মোবাইল ফোনে বার্তা টাইপ করার টাচ কীবোর্ডের মতো।",
      realWorldExampleHi = "एक इलेक्ट्रिक टाइपराइटर या आपके मोबाइल फोन पर संदेश भेजने के लिए उपयोग किए जाने वाले टच कीपैड की तरह।",
      funFactEn = "The standard 'QWERTY' key layout was invented in 1873 for mechanical typewriters to keep frequently typed letters separated!",
      funFactBn = "প্রচলিত 'QWERTY' কী লেআউটটি ১৮৭৩ সালে মেকানিকাল টাইপরাইটারের জন্য উদ্ভাবন করা হয়েছিল যাতে টাইপ করার সময় জ্যাম না হয়!",
      funFactHi = "मानक 'QWERTY' लेआउट का आविष्कार 1873 में मैकेनिकल टाइपराइटरों के लिए किया गया था ताकि कुंजियां आपस में न उलझें!",
      connectionPortEn = "Plugs into CPU via USB cable or connects wirelessly via Bluetooth.",
      connectionPortBn = "USB ক্যাবল অথবা ব্লুটুথ ওয়্যারলেসের মাধ্যমে সিপিইউর সাথে যুক্ত হয়।",
      connectionPortHi = "USB केबल या ब्लूटूथ वायरलेस के माध्यम से सीपीयू से जुड़ता है।"
    ),

    // 4. MOUSE
    HardwareComponentInfo(
      type = HardwareComponentType.MOUSE,
      nameEn = "Mouse (Pointing Device)",
      nameBn = "মাউস (পয়েন্টিং ডিভাইস)",
      nameHi = "माउस (पॉइंटिंग डिवाइस)",
      categoryEn = "Pointing Input Device",
      categoryBn = "পয়েন্টিং ইনপুট ডিভাইস",
      categoryHi = "पॉइंटिंग इनपुट डिवाइस",
      roleEn = "A handheld ergonomic controller used to move the on-screen pointer (cursor), click buttons, scroll pages, and select items.",
      roleBn = "হাতে ধরার ছোট নিয়ন্ত্রণকারী যন্ত্র যার মাধ্যমে স্ক্রিনের কার্সার চালানো, বোতামে ক্লিক করা, পেজ স্ক্রোল করা এবং আইটেম নির্বাচন করা যায়।",
      roleHi = "हाथ में पकड़ने वाला उपकरण जिसका उपयोग स्क्रीन पर कर्सर को घुमाने, बटन क्लिक करने, पेज स्क्रॉल करने और चीजें चुनने के लिए किया जाता है।",
      whatItDoesEn = "An optical LED or laser camera under the mouse snaps thousands of microscopic surface photos per second, converting motion into exact X and Y screen coordinates.",
      whatItDoesBn = "মাউসের নিচে থাকা অপটিক্যাল সেন্সর প্রতি সেকেন্ডে হাজার হাজার ক্ষুদ্র ছবি তুলে হাতের নড়াচড়াকে স্ক্রিনের সঠিক কার্সার অবস্থানে রূপান্তর করে।",
      whatItDoesHi = "माउस के नीचे लगा ऑप्टिकल सेंसर प्रति सेकंड हजारों सतह की तस्वीरें लेता है और हाथ की गति को स्क्रीन के सटीक कर्सर में बदलता है।",
      realWorldExampleEn = "Like your pointer finger pointing at an object on a shelf and selecting it by tapping it.",
      realWorldExampleBn = "দোকানের তাকের কোনো জিনিসের দিকে তর্জনী দিয়ে নির্দেশ করে আলতো ছোঁয়া দিয়ে তা বেছে নেওয়ার মতো।",
      realWorldExampleHi = "दुकान में किसी वस्तु की ओर अपनी उंगली से इशारा करने और उसे छूकर चुनने की तरह।",
      funFactEn = "The very first computer mouse was invented in 1964 by Douglas Engelbart and was carved out of a solid block of wood with two metal wheels!",
      funFactBn = "১৯৬৪ সালে ডগলাস এঙ্গেলবার্ট প্রথম কম্পিউটার মাউস তৈরি করেছিলেন যা এক টুকরো শক্ত কাঠের তৈরি ছিল এবং তাতে দুটি চাকা ছিল!",
      funFactHi = "पहला कंप्यूटर माउस 1964 में डगलस एंगेलबर्ट द्वारा लकड़ी के एक टुकड़े से बनाया गया था जिसमें दो पहिये थे!",
      connectionPortEn = "Connects to the CPU via USB cable or wireless 2.4GHz receiver / Bluetooth.",
      connectionPortBn = "USB ক্যাবল অথবা ওয়্যারলেস ডঙ্গল / ব্লুটুথ দিয়ে সিপিইউর সাথে যুক্ত হয়।",
      connectionPortHi = "USB केबल या वायरलेस डोंगल / ब्लूटूथ के माध्यम से सीपीयू से जुड़ता है।"
    )
  )

  fun getByType(type: HardwareComponentType): HardwareComponentInfo {
    return components.first { it.type == type }
  }
}

data class HardwareQuizQuestion(
  val id: Int,
  val questionEn: String,
  val questionBn: String,
  val questionHi: String,
  val optionsEn: List<String>,
  val optionsBn: List<String>,
  val optionsHi: List<String>,
  val correctOptionIndex: Int,
  val explanationEn: String,
  val explanationBn: String,
  val explanationHi: String
) {
  fun getQuestion(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> questionBn
    AppLanguage.HINDI -> questionHi
    AppLanguage.ENGLISH -> questionEn
  }

  fun getOptions(lang: AppLanguage): List<String> = when (lang) {
    AppLanguage.BENGALI -> optionsBn
    AppLanguage.HINDI -> optionsHi
    AppLanguage.ENGLISH -> optionsEn
  }

  fun getExplanation(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> explanationBn
    AppLanguage.HINDI -> explanationHi
    AppLanguage.ENGLISH -> explanationEn
  }
}

object HardwareQuizData {
  val questions: List<HardwareQuizQuestion> = listOf(
    HardwareQuizQuestion(
      id = 1,
      questionEn = "Which component acts as the central 'Brain' that processes all calculations and coordinates hardware?",
      questionBn = "কোন যন্ত্রাংশটি কম্পিউটারের 'মস্তিষ্ক' হিসেবে সমস্ত হিসাব সম্পন্ন করে এবং অন্যান্য যন্ত্রপাতি নিয়ন্ত্রণ করে?",
      questionHi = "कौन सा घटक कंप्यूटर के 'मस्तिष्क' के रूप में कार्य करता है जो सभी गणनाओं को प्रोसेस करता है?",
      optionsEn = listOf("Monitor", "CPU / System Unit", "Mouse", "Keyboard"),
      optionsBn = listOf("মনিটর", "সিপিইউ / সিস্টেম ইউনিট", "মাউস", "কীবোর্ড"),
      optionsHi = listOf("मॉनिटर", "सीपीयू / सिस्टम यूनिट", "माउस", "कीबोर्ड"),
      correctOptionIndex = 1,
      explanationEn = "The CPU (Central Processing Unit) inside the System Unit executes code, calculates data, and manages all system operations.",
      explanationBn = "সিস্টেম ইউনিটের ভেতরের সিপিইউ (সেন্ট্রাল প্রসেসিং ইউনিট) সমস্ত নির্দেশ কার্যকর করে এবং গাণিতিক হিসাব চালায়।",
      explanationHi = "सिस्टम यूनिट के अंदर सीपीयू (सेंट्रल प्रोसेसिंग यूनिट) सभी निर्देशों को निष्पादित करता है और गणना करता है।"
    ),
    HardwareQuizQuestion(
      id = 2,
      questionEn = "Which hardware device outputs visual pictures, text, and videos to your eyes?",
      questionBn = "কোন হার্ডওয়্যার ডিভাইসটি আমাদের চোখের সামনে লেখা, ছবি এবং ভিডিও দৃশ্যমান করে?",
      questionHi = "कौन सा हार्डवेयर उपकरण हमारी आंखों के सामने चित्र, टेक्स्ट और वीडियो प्रदर्शित करता है?",
      optionsEn = listOf("Monitor", "Keyboard", "Optical Mouse", "Hard Drive"),
      optionsBn = listOf("মনিটর", "কীবোর্ড", "অপটিক্যাল মাউস", "হার্ড ড্রাইভ"),
      optionsHi = listOf("मॉनिटर", "कीबोर्ड", "ऑप्टिकल माउस", "हार्ड ड्राइव"),
      correctOptionIndex = 0,
      explanationEn = "The Monitor is a visual output device that illuminates millions of colored pixels to display graphics.",
      explanationBn = "মনিটর হলো একটি ভিজ্যুয়াল আউটপুট ডিভাইস যা লক্ষ লক্ষ রঙিন পিক্সেল জ্বালিয়ে ছবি প্রদর্শন করে।",
      explanationHi = "मॉनिटर एक विज़ुअल आउटपुट डिवाइस है जो लाखों रंगीन पिक्सेल रोशन करके तस्वीरें दिखाता है।"
    ),
    HardwareQuizQuestion(
      id = 3,
      questionEn = "Which peripheral device is specifically designed for typing letters, numbers, and shortcut commands?",
      questionBn = "কোন ইনপুট যন্ত্রটি বিশেষভাবে অক্ষর, সংখ্যা এবং শর্টকাট কমান্ড টাইপ করার জন্য তৈরি?",
      questionHi = "कौन सा उपकरण विशेष रूप से अक्षर, संख्याएं और शॉर्टकट कमांड टाइप करने के लिए डिज़ाइन किया गया है?",
      optionsEn = listOf("Mouse", "Speaker", "Keyboard", "Webcam"),
      optionsBn = listOf("মাউস", "স্পিকার", "কীবোর্ড", "ওয়েবক্যাম"),
      optionsHi = listOf("माउस", "स्पीकर", "कीबोर्ड", "वेबकैम"),
      correctOptionIndex = 2,
      explanationEn = "The Keyboard provides alphanumeric keys and function buttons to input text directly into the computer.",
      explanationBn = "কীবোর্ডে বর্ণমালা ও সংখ্যার কী থাকে যার সাহায্যে কম্পিউটারে সরাসরি লেখা ইনপুট দেওয়া যায়।",
      explanationHi = "कीबोर्ड में अक्षर और संख्या की कुंजियां होती हैं जिनके द्वारा कंप्यूटर में टेक्स्ट इनपुट किया जाता है।"
    ),
    HardwareQuizQuestion(
      id = 4,
      questionEn = "Which handheld pointing device translates physical hand movements on a desk into cursor coordinates on screen?",
      questionBn = "কোন পয়েন্টিং ডিভাইসটি টেবিলের ওপর হাতের নড়াচড়াকে স্ক্রিনে কার্সারের অবস্থানে রূপান্তর করে?",
      questionHi = "कौन सा उपकरण मेज पर हाथ की गति को स्क्रीन पर कर्सर की स्थिति में बदलता है?",
      optionsEn = listOf("Mouse", "Monitor", "CPU Tower", "Power Supply"),
      optionsBn = listOf("মাউস", "মনিটর", "সিপিইউ টাওয়ার", "পাওয়ার সাপ্লাই"),
      optionsHi = listOf("माउस", "मॉनिटर", "सीपीयू टॉवर", "पावर सप्लाई"),
      correctOptionIndex = 0,
      explanationEn = "The Mouse utilizes an optical sensor to track movement across your desk, allowing pointing, clicking, and dragging.",
      explanationBn = "মাউস তার অপটিক্যাল সেন্সরের সাহায্যে টেবিলের গতিবিধি ট্র্যাক করে পয়েন্ট, ক্লিক ও ড্র্যাগ করার সুযোগ দেয়।",
      explanationHi = "माउस अपने ऑप्टिकल सेंसर से गति को ट्रैक करके स्क्रीन पर पॉइंट, क्लिक और ड्रैग करने की सुविधा देता है।"
    )
  )
}
