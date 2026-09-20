package com.example.data.model

import com.example.util.AppLanguage

// =============================================================================
// CPU INTERNAL SUBCOMPONENTS
// =============================================================================
enum class CpuInternalPart {
  CHIP_DIE,
  CONTROL_UNIT,
  ALU,
  REGISTERS,
  CACHE,
  CORES,
  CLOCK,
  HEAT_COOLING
}

data class CpuPartDetail(
  val part: CpuInternalPart,
  val nameEn: String,
  val nameBn: String,
  val nameHi: String,
  val subtitleEn: String,
  val subtitleBn: String,
  val subtitleHi: String,
  val whatItIsEn: String,
  val whatItIsBn: String,
  val whatItIsHi: String,
  val whatItDoesEn: String,
  val whatItDoesBn: String,
  val whatItDoesHi: String,
  val howItWorksEn: String,
  val howItWorksBn: String,
  val howItWorksHi: String,
  val whyImportantEn: String,
  val whyImportantBn: String,
  val whyImportantHi: String,
  val realLifeExampleEn: String,
  val realLifeExampleBn: String,
  val realLifeExampleHi: String
) {
  fun getName(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> nameBn
    AppLanguage.HINDI -> nameHi
    AppLanguage.ENGLISH -> nameEn
  }

  fun getSubtitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> subtitleBn
    AppLanguage.HINDI -> subtitleHi
    AppLanguage.ENGLISH -> subtitleEn
  }

  fun getWhatItIs(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> whatItIsBn
    AppLanguage.HINDI -> whatItIsHi
    AppLanguage.ENGLISH -> whatItIsEn
  }

  fun getWhatItDoes(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> whatItDoesBn
    AppLanguage.HINDI -> whatItDoesHi
    AppLanguage.ENGLISH -> whatItDoesEn
  }

  fun getHowItWorks(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> howItWorksBn
    AppLanguage.HINDI -> howItWorksHi
    AppLanguage.ENGLISH -> howItWorksEn
  }

  fun getWhyImportant(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> whyImportantBn
    AppLanguage.HINDI -> whyImportantHi
    AppLanguage.ENGLISH -> whyImportantEn
  }

  fun getRealLifeExample(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> realLifeExampleBn
    AppLanguage.HINDI -> realLifeExampleHi
    AppLanguage.ENGLISH -> realLifeExampleEn
  }
}

// =============================================================================
// RAM SUBCOMPONENTS
// =============================================================================
enum class RamPartType {
  RAM_STICK,
  MEMORY_CHIPS,
  DIMM_SLOTS,
  DATA_BUS
}

data class RamPartDetail(
  val type: RamPartType,
  val nameEn: String,
  val nameBn: String,
  val nameHi: String,
  val whatItIsEn: String,
  val whatItIsBn: String,
  val whatItIsHi: String,
  val whatItDoesEn: String,
  val whatItDoesBn: String,
  val whatItDoesHi: String,
  val realLifeAnalogyEn: String,
  val realLifeAnalogyBn: String,
  val realLifeAnalogyHi: String
) {
  fun getName(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> nameBn
    AppLanguage.HINDI -> nameHi
    AppLanguage.ENGLISH -> nameEn
  }
  fun getWhatItIs(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> whatItIsBn
    AppLanguage.HINDI -> whatItIsHi
    AppLanguage.ENGLISH -> whatItIsEn
  }
  fun getWhatItDoes(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> whatItDoesBn
    AppLanguage.HINDI -> whatItDoesHi
    AppLanguage.ENGLISH -> whatItDoesEn
  }
  fun getAnalogy(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> realLifeAnalogyBn
    AppLanguage.HINDI -> realLifeAnalogyHi
    AppLanguage.ENGLISH -> realLifeAnalogyEn
  }
}

// =============================================================================
// COMPARISON ITEM (CPU vs RAM vs ROM)
// =============================================================================
data class HardwareComparisonRow(
  val featureEn: String,
  val featureBn: String,
  val featureHi: String,
  val cpuValueEn: String,
  val cpuValueBn: String,
  val cpuValueHi: String,
  val ramValueEn: String,
  val ramValueBn: String,
  val ramValueHi: String,
  val romValueEn: String,
  val romValueBn: String,
  val romValueHi: String
) {
  fun getFeature(lang: AppLanguage) = when (lang) {
    AppLanguage.BENGALI -> featureBn
    AppLanguage.HINDI -> featureHi
    AppLanguage.ENGLISH -> featureEn
  }
  fun getCpu(lang: AppLanguage) = when (lang) {
    AppLanguage.BENGALI -> cpuValueBn
    AppLanguage.HINDI -> cpuValueHi
    AppLanguage.ENGLISH -> cpuValueEn
  }
  fun getRam(lang: AppLanguage) = when (lang) {
    AppLanguage.BENGALI -> ramValueBn
    AppLanguage.HINDI -> ramValueHi
    AppLanguage.ENGLISH -> ramValueEn
  }
  fun getRom(lang: AppLanguage) = when (lang) {
    AppLanguage.BENGALI -> romValueBn
    AppLanguage.HINDI -> romValueHi
    AppLanguage.ENGLISH -> romValueEn
  }
}

// =============================================================================
// WORKFLOW / PIPELINE STAGES
// =============================================================================
enum class PipelineStage {
  USER_INPUT,
  STORAGE,
  RAM,
  CPU,
  OUTPUT
}

data class PipelineStageInfo(
  val stage: PipelineStage,
  val stepNumber: Int,
  val titleEn: String,
  val titleBn: String,
  val titleHi: String,
  val subtitleEn: String,
  val subtitleBn: String,
  val subtitleHi: String,
  val descEn: String,
  val descBn: String,
  val descHi: String,
  val browserActionEn: String,
  val browserActionBn: String,
  val browserActionHi: String
) {
  fun getTitle(lang: AppLanguage) = when (lang) {
    AppLanguage.BENGALI -> titleBn
    AppLanguage.HINDI -> titleHi
    AppLanguage.ENGLISH -> titleEn
  }
  fun getSubtitle(lang: AppLanguage) = when (lang) {
    AppLanguage.BENGALI -> subtitleBn
    AppLanguage.HINDI -> subtitleHi
    AppLanguage.ENGLISH -> subtitleEn
  }
  fun getDesc(lang: AppLanguage) = when (lang) {
    AppLanguage.BENGALI -> descBn
    AppLanguage.HINDI -> descHi
    AppLanguage.ENGLISH -> descEn
  }
  fun getBrowserAction(lang: AppLanguage) = when (lang) {
    AppLanguage.BENGALI -> browserActionBn
    AppLanguage.HINDI -> browserActionHi
    AppLanguage.ENGLISH -> browserActionEn
  }
}

// =============================================================================
// 8-QUESTION DETAILED QUIZ
// =============================================================================
data class CpuRamRomQuizQuestion(
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
  fun getQuestion(lang: AppLanguage) = when (lang) {
    AppLanguage.BENGALI -> questionBn
    AppLanguage.HINDI -> questionHi
    AppLanguage.ENGLISH -> questionEn
  }
  fun getOptions(lang: AppLanguage) = when (lang) {
    AppLanguage.BENGALI -> optionsBn
    AppLanguage.HINDI -> optionsHi
    AppLanguage.ENGLISH -> optionsEn
  }
  fun getExplanation(lang: AppLanguage) = when (lang) {
    AppLanguage.BENGALI -> explanationBn
    AppLanguage.HINDI -> explanationHi
    AppLanguage.ENGLISH -> explanationEn
  }
}

// =============================================================================
// REPOSITORY PROVIDER
// =============================================================================
object CpuRamRomLessonRepository {

  val cpuParts: List<CpuPartDetail> = listOf(
    CpuPartDetail(
      part = CpuInternalPart.CHIP_DIE,
      nameEn = "CPU Silicon Die & Heatspreader",
      nameBn = "সিপিইউ সিলিকন ডাই এবং হিটস্প্রেডার",
      nameHi = "सीपीयू सिलिकॉन डाई और हीटस्प्रेडर",
      subtitleEn = "Central Processing Unit (The Brain)",
      subtitleBn = "সেন্ট্রাল প্রসেসিং ইউনিট (মস্তিষ্ক)",
      subtitleHi = "सेंट्रल प्रोसेसिंग यूनिट (कंप्यूटर का मस्तिष्क)",
      whatItIsEn = "A tiny slice of hyper-purified silicon packed with billions of microscopic microscopic transistors (switches), protected beneath a nickel-plated copper lid.",
      whatItIsBn = "অত্যন্ত বিশুদ্ধ সিলিকনের ক্ষুদ্র টুকরা যার মধ্যে কোটি কোটি আণুবীক্ষণিক ট্রানজিস্টর (সুইচ) থাকে, যা তামার মেটালিক ঢাকনা দিয়ে সুরক্ষিত।",
      whatItIsHi = "अति-शुद्ध सिलिकॉन का एक छोटा टुकड़ा जिसमें अरबों सूक्ष्म ट्रांजिस्टर (स्विच) भरे होते हैं, जो निकल-प्लेटेड तांबे के ढक्कन से सुरक्षित रहता है।",
      whatItDoesEn = "Executes computer programs by calculating numbers, comparing conditions, and coordinating every hardware component.",
      whatItDoesBn = "কম্পিউটার প্রোগ্রাম চালায়, গাণিতিক হিসাব করে, শর্ত যাচাই করে এবং কম্পিউটারের সমস্ত যন্ত্রাংশ পরিচালনা করে।",
      whatItDoesHi = "कंप्यूटर प्रोग्राम चलाता है, गणितीय गणना करता है, शर्तों की जांच करता है और कंप्यूटर के सभी घटकों का समन्वय करता है।",
      howItWorksEn = "Operates in the Fetch-Decode-Execute cycle: Grabs instruction from RAM, decodes meaning, computes result, and writes data back.",
      howItWorksBn = "ফেচ-ডিকোড-এক্সিকিউট চক্রে কাজ করে: র‍্যাম থেকে নির্দেশ আনে, অর্থ বোঝে, ফলাফল হিসাব করে এবং ডেটা জমা রাখে।",
      howItWorksHi = "फेच-डिकोड-एक्ज़ीक्यूट चक्र में काम करता है: रैम से निर्देश लाता है, समझता है, परिणाम निकालता है और डेटा लिखता है।",
      whyImportantEn = "Without the CPU, a computer is just cold, lifeless metal. Everything from mouse clicks to video games requires CPU calculations.",
      whyImportantBn = "সিপিইউ ছাড়া কম্পিউটার নিথর ধাতু মাত্র। মাউস ক্লিক থেকে শুরু করে হাই-এন্ড ভিডিও গেম—সবকিছু সিপিইউ হিসাব করে।",
      whyImportantHi = "सीपीयू के बिना कंप्यूटर सिर्फ बेजान धातु है। माउस क्लिक से लेकर वीडियो गेम तक हर चीज की गणना सीपीयू करता है।",
      realLifeExampleEn = "Like the CEO of a company who makes all strategic decisions and directs departments.",
      realLifeExampleBn = "একটি অফিসের প্রধান পরিচালকের (CEO) মতো, যিনি সমস্ত সিদ্ধান্ত নেন এবং প্রতিটি বিভাগ পরিচালনা করেন।",
      realLifeExampleHi = "किसी कंपनी के मुख्य कार्यकारी (CEO) की तरह जो सभी निर्णय लेते हैं और विभागों का संचालन करते हैं।"
    ),
    CpuPartDetail(
      part = CpuInternalPart.CONTROL_UNIT,
      nameEn = "Control Unit (CU)",
      nameBn = "কন্ট্রোল ইউনিট (CU)",
      nameHi = "कंट्रोल यूनिट (CU)",
      subtitleEn = "The Orchestrator & Traffic Controller",
      subtitleBn = "নির্দেশ পরিচালক এবং ট্রাফিক নিয়ন্ত্রক",
      subtitleHi = "निर्देश नियंत्रक और ट्रैफिक पुलिस",
      whatItIsEn = "The supervisory circuitry inside the CPU that directs the flow of data between memory, ALU, and input/output devices.",
      whatItIsBn = "সিপিইউ-এর ভেতরের প্রধান নিয়ন্ত্রণ সার্কিট যা মেমরি, এএলইউ এবং ইনপুট/আউটপুট ডিভাইসের মধ্যে ডেটা প্রবাহ পরিচালনা করে।",
      whatItIsHi = "सीपीयू के अंदर का मुख्य नियंत्रण सर्किट जो मेमोरी, एएलयू और इनपुट/आउटपुट के बीच डेटा प्रवाह को नियंत्रित करता है।",
      whatItDoesEn = "Fetches instructions from RAM, decodes what they mean into microcode signals, and tells other units when and what to calculate.",
      whatItDoesBn = "র‍্যাম থেকে নির্দেশ এনে ডিকোড করে এবং অন্যান্য ইউনিটকে বলে দেয় কখন কী হিসাব করতে হবে।",
      whatItDoesHi = "रैम से निर्देशों को फेच करता है, डिकोड करता है और अन्य घटकों को बताता है कि कब क्या करना है।",
      howItWorksEn = "Uses the Program Counter (PC) to track the address of the next instruction, sending control pulses synchronized with the system clock.",
      howItWorksBn = "প্রোগ্রাম কাউন্টারের মাধ্যমে পরবর্তী নির্দেশের ঠিকানা মনে রাখে এবং ক্লক পালসের সাথে মিল রেখে সংকেত পাঠায়।",
      howItWorksHi = "प्रोग्राम काउंटर की मदद से अगले निर्देश का पता रखता है और क्लॉक सिग्नल के साथ समन्वय करके काम करता है।",
      whyImportantEn = "Without the CU, the ALU would not know what to calculate, and RAM data would collide like cars at an intersection with no traffic light.",
      whyImportantBn = "কন্ট্রোল ইউনিট না থাকলে এএলইউ জানতেই পারত না কী হিসাব করতে হবে, ট্রাফিক সিগন্যাল ছাড়া রাস্তার মতো জ্যাম লেগে যেত।",
      whyImportantHi = "कंट्रोल यूनिट के बिना एएलयू को पता ही नहीं चलेगा कि क्या गणना करनी है, जैसे बिना ट्रैफिक लाइट का चौराहा।",
      realLifeExampleEn = "Like an air traffic controller directing airplanes onto runways in perfect order without crashes.",
      realLifeExampleBn = "এয়ার ট্রাফিক কন্ট্রোলারের মতো, যিনি বিমানবন্দরে প্রতিটি বিমানকে ক্রমানুসারে নিরাপদে ওঠা-নামা করান।",
      realLifeExampleHi = "एयर ट्रैफिक कंट्रोलर की तरह, जो हवाई जहाजों को सही समय पर सुरक्षित रूप से उड़ने और उतरने का निर्देश देता है।"
    ),
    CpuPartDetail(
      part = CpuInternalPart.ALU,
      nameEn = "Arithmetic Logic Unit (ALU)",
      nameBn = "অ্যারিথমেটিক লজিক ইউনিট (ALU)",
      nameHi = "अंकगणित और तार्किक इकाई (ALU)",
      subtitleEn = "The Math & Decision Engine",
      subtitleBn = "গণিত এবং সিদ্ধান্ত গ্রহণের মূল যন্ত্র",
      subtitleHi = "गणित और निर्णय लेने का मुख्य इंजन",
      whatItIsEn = "The high-speed digital calculator inside the CPU that performs all arithmetic operations (+, -, ×, ÷) and logic evaluations (AND, OR, NOT, <, >).",
      whatItIsBn = "সিপিইউ-এর অতি দ্রুত ডিজিটাল ক্যালকুলেটর যা সমস্ত গাণিতিক হিসাব এবং যৌক্তিক সিদ্ধান্ত (তুলনা) সম্পন্ন করে।",
      whatItIsHi = "सीपीयू के अंदर का सुपर-फास्ट डिजिटल कैलकुलेटर जो सभी गणितीय गणनाएं और तार्किक निर्णय (तुलना) करता है।",
      whatItDoesEn = "Takes numbers from registers, adds or compares them, and produces exact mathematical results in fractions of a nanosecond.",
      whatItDoesBn = "রেজিস্টার থেকে সংখ্যা নিয়ে যোগ, বিয়োগ বা তুলনা করে ন্যানোসেকেন্ডেরও কম সময়ে ফলাফল বের করে।",
      whatItDoesHi = "रजिस्टर से संख्याएं लेकर उन्हें जोड़ता, घटाता या तुलना करता है और नैनोसेकंड में सटीक परिणाम देता है।",
      howItWorksEn = "Constructed from binary logic gates (Adders, Comparators, Multiplexers) that operate directly on 1s and 0s.",
      howItWorksBn = "বাইনারি লজিক গেট (অ্যাডার, মাল্টিপ্লেক্সার) দিয়ে তৈরি যা সরাসরি ১ এবং ০ দিয়ে বিদ্যুৎ সংকেতে কাজ করে।",
      howItWorksHi = "बाइनरी लॉजिक गेट्स से बना होता है जो सीधे 1 और 0 पर इलेक्ट्रॉनिक सिग्नल के रूप में कार्य करते हैं।",
      whyImportantEn = "Every single digital action—from moving a game character to playing an MP3 song—is fundamentally arithmetic solved by the ALU.",
      whyImportantBn = "গেম খেলা থেকে গান শোনা—কম্পিউটারের প্রতিটি কাজই দিনশেষে এএলইউ-এর গাণিতিক সমীকরণ সমাধানের ফসল।",
      whyImportantHi = "कंप्यूटर पर गेम खेलने से लेकर गाने सुनने तक हर कार्य अंततः एएलयू द्वारा की गई गणितीय गणना ही है।",
      realLifeExampleEn = "Like a master mathematician with a turbo calculator solving hundreds of millions of problems per second.",
      realLifeExampleBn = "একজন অত্যন্ত দ্রুত গণিতবিদের মতো, যিনি প্রতি সেকেন্ডে কোটি কোটি গণিত নির্ভুলভাবে সমাধান করতে পারেন।",
      realLifeExampleHi = "एक अत्यंत कुशल गणितज्ञ की तरह जो प्रति सेकंड करोड़ों गणितीय समस्याओं को हल कर सकता है।"
    ),
    CpuPartDetail(
      part = CpuInternalPart.REGISTERS,
      nameEn = "Registers",
      nameBn = "রেজিস্টারস (Registers)",
      nameHi = "रजिस्टर्स (Registers)",
      subtitleEn = "The Fastest Memory in the World",
      subtitleBn = "পৃথিবীর সবচেয়ে দ্রুতগতির মেমরি",
      subtitleHi = "दुनिया की सबसे तेज़ मेमोरी",
      whatItIsEn = "Tiny, high-speed storage slots located right inside the core alongside the ALU and CU, holding active 32-bit or 64-bit numbers.",
      whatItIsBn = "সিপিইউ কোরের ভেতরে এএলইউ-এর ঠিক পাশে অবস্থিত অতি ক্ষুদ্র ও দ্রুতগতির মেমরি স্লট যা তাৎক্ষণিক সংখ্যা ধরে রাখে।",
      whatItIsHi = "सीपीयू कोर के अंदर स्थित बहुत छोटी और अत्यधिक तीव्र गति वाली मेमोरी जो सक्रिय गणना के दौरान डेटा रखती है।",
      whatItDoesEn = "Stores the immediate values that the ALU is currently calculating, intermediate results, and memory address pointers.",
      whatItDoesBn = "এএলইউ এই মুহূর্তে যে সংখ্যাগুলো নিয়ে হিসাব করছে এবং অন্তর্বর্তীকালীন ফলাফল এখানে ধরে রাখে।",
      whatItDoesHi = "एएलयू द्वारा वर्तमान में गणना किए जा रहे तात्कालिक मानों और पतों को तुरंत उपलब्ध कराता है।",
      howItWorksEn = "Can be read and written in a single clock cycle (less than 0.3 nanoseconds), vastly faster than even L1 Cache or RAM.",
      howItWorksBn = "একটি ক্লক সাইকেলেই (০.৩ ন্যানোসেকেন্ডেরও কম) ডেটা পড়তে ও লিখতে পারে, যা র‍্যামের চেয়ে হাজার গুণ দ্রুত।",
      howItWorksHi = "मात्र एक क्लॉक चक्र (0.3 नैनोसेकंड से कम) में डेटा पढ़ और लिख सकता है, जो रैम से हजारों गुना तेज़ है।",
      whyImportantEn = "If the CPU had to wait for RAM to fetch every intermediate number in an equation, computers would run hundreds of times slower.",
      whyImportantBn = "প্রতিটি ছোট সংখ্যার জন্য সিপিইউ-কে যদি র‍্যামে যেতে হতো, তবে কম্পিউটার শতগুণ ধীরগতির হয়ে যেত।",
      whyImportantHi = "यदि सीपीयू को हर छोटी संख्या के लिए रैम तक जाना पड़ता, तो कंप्यूटर सैकड़ों गुना धीमा हो जाता।",
      realLifeExampleEn = "Like holding numbers in your active short-term mental focus while doing mental math in your head.",
      realLifeExampleBn = "মুখে মুখে কোনো হিসাব করার সময় মাথায় যে তাৎক্ষণিক সংখ্যাগুলো আমরা এক মুহূর্তের জন্য মনে রাখি।",
      realLifeExampleHi = "मन में गणित करते समय दिमाग में तुरंत याद रखी जाने वाली छोटी-छोटी संख्याओं की तरह।"
    ),
    CpuPartDetail(
      part = CpuInternalPart.CACHE,
      nameEn = "CPU Cache (L1, L2, L3)",
      nameBn = "সিপিইউ ক্যাশ মেমরি (L1, L2, L3)",
      nameHi = "सीपीयू कैश मेमोरी (L1, L2, L3)",
      subtitleEn = "Ultra-Fast On-Die Buffer",
      subtitleBn = "সিপিইউ-এর নিজস্ব সুপারফাস্ট মেমরি",
      subtitleHi = "अति-तीव्र ऑन-डाई बफर मेमोरी",
      whatItIsEn = "High-speed SRAM built directly on the CPU chip between the registers and main system RAM, divided into Level 1, Level 2, and Level 3.",
      whatItIsBn = "সিপিইউ চিপের ভেতরে থাকা অতি দ্রুত স্ট্যাটিক র‍্যাম (SRAM), যা L1, L2 এবং L3 এই তিন স্তরে বিভক্ত।",
      whatItIsHi = "सीपीयू चिप के अंदर सीधे बनी हाई-स्पीड मेमोरी, जो लेवल 1, लेवल 2 और लेवल 3 (L1, L2, L3) में विभाजित होती है।",
      whatItDoesEn = "Keeps copies of frequently used instructions and data right next to the processor so it doesn't have to wait for slower RAM.",
      whatItDoesBn = "বারবার প্রয়োজন হওয়া নির্দেশ এবং ডেটা সিপিইউ-এর সবচেয়ে কাছে জমা রাখে যাতে ধীরগতির র‍্যামের জন্য অপেক্ষা করতে না হয়।",
      whatItDoesHi = "बार-बार उपयोग होने वाले निर्देशों की प्रतियां सीपीयू के पास रखता है ताकि धीमी रैम पर निर्भर न रहना पड़े।",
      howItWorksEn = "L1 is fastest (per core), L2 is medium-sized, and L3 is large (shared across all cores). Hits in cache take 1–10 nanoseconds.",
      howItWorksBn = "L1 সবচেয়ে দ্রুত, L2 মাঝারি এবং L3 সব কোরের মধ্যে শেয়ার করা থাকে। ক্যাশ থেকে ডেটা পেতে মাত্র ১-১০ ন্যানোসেকেন্ড লাগে।",
      howItWorksHi = "L1 सबसे तेज़ है, L2 मध्यम है और L3 बड़ा व सभी कोर द्वारा साझा किया जाता है। इसमें 1-10 नैनोसेकंड लगते हैं।",
      whyImportantEn = "Main RAM takes 50–70 nanoseconds to respond. Cache prevents CPU cores from sitting idle starving for data (cache misses).",
      whyImportantBn = "মূল র‍্যাম থেকে ডেটা আসতে ৫০-৭০ ন্যানোসেকেন্ড সময় লাগে। ক্যাশ সিপিইউ-কে অলস বসে থাকা থেকে বাঁচায়।",
      whyImportantHi = "रैम से डेटा आने में 50-70 नैनोसेकंड लगते हैं। कैश मेमोरी सीपीयू को खाली बैठने से बचाती है।",
      realLifeExampleEn = "Like keeping your favorite notebook and pen right on your desk rather than walking to the library storage shelf every minute.",
      realLifeExampleBn = "লাইব্রেরির আলমারিতে বারবার না গিয়ে প্রিয় বই ও খাতা পড়ার টেবিলের উপরে হাতের কাছে রাখার মতো।",
      realLifeExampleHi = "हर बार अलमारी में जाने के बजाय अपनी पसंदीदा किताब और पेन को अपनी मेज पर पास में रखने की तरह।"
    ),
    CpuPartDetail(
      part = CpuInternalPart.CORES,
      nameEn = "CPU Cores (Single vs Multi-Core)",
      nameBn = "সিপিইউ কোর (একক বনাম মাল্টি-কোর)",
      nameHi = "सीपीयू कोर (सिंगल बनाम मल्टी-कोर)",
      subtitleEn = "Independent Processing Engines",
      subtitleBn = "স্বাধীন প্রসেসিং ইঞ্জিনসমূহ",
      subtitleHi = "स्वतंत्र प्रोसेसिंग इंजन",
      whatItIsEn = "A core is an independent processing unit containing its own ALU, CU, and registers capable of executing an entire program thread.",
      whatItIsBn = "কোর হলো সিপিইউ-এর ভেতরের একটি সম্পূর্ণ স্বাধীন প্রসেসিং ইঞ্জিন যার নিজস্ব এএলইউ, কন্ট্রোল ইউনিট ও রেজিস্টার রয়েছে।",
      whatItIsHi = "कोर सीपीयू के अंदर एक स्वतंत्र प्रोसेसिंग यूनिट है जिसमें अपना एएलयू, सीयू और रजिस्टर होते हैं।",
      whatItDoesEn = "Multi-core processors (Dual-Core, Quad-Core, Octa-Core, 16-Core) execute multiple tasks completely simultaneously in true parallel.",
      whatItDoesBn = "মাল্টি-কোর প্রসেসর একই সাথে একাধিক কাজ কোনো রকম আটকে যাওয়া ছাড়াই পুরোপুরি সমান্তরালে সম্পন্ন করতে পারে।",
      whatItDoesHi = "मल्टी-कोर प्रोसेसर एक साथ कई अलग-अलग कार्यों को बिना किसी रुकावट के समानांतर रूप से चलाते हैं।",
      howItWorksEn = "One core can play your Spotify music, a second core renders a YouTube 4K stream, while a third runs your antivirus scan.",
      howItWorksBn = "একটি কোর গান বাজাতে পারে, দ্বিতীয় কোর ব্রাউজারে ভিডিও চালাতে পারে এবং তৃতীয় কোর অ্যান্টিভাইরাস স্ক্যান করতে পারে।",
      howItWorksHi = "एक कोर गाना बजा सकता है, दूसरा कोर ब्राउज़र में वीडियो चला सकता है और तीसरा कोर एंटीवायरस चला सकता है।",
      whyImportantEn = "A single fast core can only juggle one task at a time. Multiple cores enable effortless multitasking and professional editing.",
      whyImportantBn = "একটি মাত্র কোর থাকলে কম্পিউটারকে পাল্টাপাল্টি কাজ করতে হয়। বেশি কোর থাকলে একসাথে অনেক কাজ নির্বিঘ্নে চলে।",
      whyImportantHi = "सिंगल कोर को बार-बार काम बदलना पड़ता है। ज्यादा कोर होने से कंप्यूटर बिना अटके स्मूद मल्टीटास्किंग करता है।",
      realLifeExampleEn = "Like a supermarket checkout: 1 cashier vs 8 cashiers serving customers simultaneously.",
      realLifeExampleBn = "সুপারমার্কেটের ক্যাশ কাউন্টারের মতো: ১ জন ক্যাশিয়ারের বদলে ৮ জন ক্যাশিয়ার থাকলে ৮ গুণ দ্রুত খরিদ্দার বিদায় হয়।",
      realLifeExampleHi = "सुपरमार्केट के काउंटर की तरह: 1 कैशियर के बजाय 8 कैशियर होने से ग्राहक तेजी से निपटते हैं।"
    ),
    CpuPartDetail(
      part = CpuInternalPart.CLOCK,
      nameEn = "CPU Clock & Frequency (GHz)",
      nameBn = "সিপিইউ ক্লক ও ফ্রিকোয়েন্সি (GHz)",
      nameHi = "सीपीयू क्लॉक और फ्रीक्वेंसी (GHz)",
      subtitleEn = "The Rhythmic Metronome of Computation",
      subtitleBn = "গণনার ছন্দের মূল তাল ও গতি",
      subtitleHi = "कंप्यूटिंग की ताल और गति",
      whatItIsEn = "A quartz crystal oscillator that vibrates millions or billions of times every second, sending an electrical pulse that synchronizes all chip circuitry.",
      whatItIsBn = "কোয়ার্টজ ক্রিস্টাল যা প্রতি সেকেন্ডে কোটি বা শতকোটি বার স্পন্দিত হয়ে বিদ্যুৎ সংকেত পাঠিয়ে সব সার্কিটকে একই ছন্দে চালায়।",
      whatItIsHi = "क्वार्ट्ज क्रिस्टल जो प्रति सेकंड अरबों बार कंपन करता है और सभी सर्किटों को एक साथ तालमेल में रखने के लिए सिग्नल भेजता है।",
      whatItDoesEn = "Dictates the cycle pace: In 1 Hertz (Hz), 1 cycle occurs. 1 Gigahertz (GHz) means 1,000,000,000 (one billion) clock ticks per second!",
      whatItDoesBn = "কাজের গতি নির্ধারণ করে: ১ গিগাহার্টজ (1 GHz) মানে প্রতি সেকেন্ডে একশো কোটি (১,০০০,০০০,০০০) ক্লক টিক সম্পন্ন হয়!",
      whatItDoesHi = "गति तय करता है: 1 गीगाहर्ट्ज (1 GHz) का मतलब है कि प्रति सेकंड 1 अरब (1,000,000,000) क्लॉक चक्र पूरे होते हैं!",
      howItWorksEn = "During each tick, transistors switch states and instructions advance along the pipeline.",
      howItWorksBn = "প্রতিটি ট্রিগারে ট্রানজিস্টরগুলো নিজেদের অবস্থা পরিবর্তন করে এবং নির্দেশগুলো এক ধাপ এগিয়ে যায়।",
      howItWorksHi = "हर टिक पर ट्रांजिस्टर अपनी स्थिति बदलते हैं और निर्देश पाइपलाइन में आगे बढ़ते हैं।",
      whyImportantEn = "Higher GHz does NOT always mean faster! A modern CPU doing 4 instructions per cycle (IPC) at 3 GHz beats an old CPU doing 1 IPC at 4 GHz.",
      whyImportantBn = "মনে রাখবেন: বেশি GHz মানেই সবসময় দ্রুত নয়! আধুনিক প্রসেসর প্রতিটিকে বেশি কাজ (IPC) করে কম GHz-এও দ্রুত কাজ করে।",
      whyImportantHi = "ध्यान दें: ज्यादा GHz का मतलब हमेशा तेज होना नहीं है! नई तकनीक की चिप कम GHz पर भी पुराने प्रोसेसर से तेज काम करती है।",
      realLifeExampleEn = "Like a fitness instructor clapping hands to set the exact pace of pushups for the whole gym.",
      realLifeExampleBn = "জিম ট্রেইনারের তালি বাজিয়ে ব্যায়ামের তাল ও গতি নিয়ন্ত্রণ করার মতো।",
      realLifeExampleHi = "जिम ट्रेनर द्वारा ताली बजाकर सभी को एक ही लय और गति में व्यायाम कराने की तरह।"
    ),
    CpuPartDetail(
      part = CpuInternalPart.HEAT_COOLING,
      nameEn = "Heat, Thermal Paste & Heatsink",
      nameBn = "তাপ, থার্মাল পেস্ট ও কুলিং ফ্যান",
      nameHi = "तापमान, थर्मल पेस्ट और कूलिंग फैन",
      subtitleEn = "Thermal Throttling & Protection",
      subtitleBn = "থার্মাল থ্রটলিং এবং তাপমাত্রা সুরক্ষা",
      subtitleHi = "थर्मल थ्रॉटलिंग और तापमान नियंत्रण",
      whatItIsEn = "Electrical resistance through billions of transistors creates intense heat. Metal heat pipes, copper baseplates, thermal paste, and fans dissipate this heat.",
      whatItIsBn = "কোটি কোটি ট্রানজিস্টরে বিদ্যুতের প্রবাহ প্রচণ্ড উত্তাপ সৃষ্টি করে। তামার পাইপ, অ্যালুমিনিয়াম ফিন এবং কুলিং ফ্যান সেই তাপ বের করে দেয়।",
      whatItIsHi = "अरबों ट्रांजिस्टर में बिजली के प्रवाह से भारी गर्मी पैदा होती है। कॉपर पाइप, थर्मल पेस्ट और पंखे इस गर्मी को बाहर निकालते हैं।",
      whatItDoesEn = "Keeps CPU safe under 80°C–90°C. If it gets too hot (100°C), the CPU automatically throttles down its clock speed to prevent physical melting!",
      whatItDoesBn = "সিপিইউ-কে ৮০-৯০ ডিগ্রি সেলসিয়াসের নিচে রাখে। বেশি গরম হলে সিপিইউ ক্ষতি এড়াতে নিজেই গতি কমিয়ে দেয় (থার্মাল থ্রটলিং)।",
      whatItDoesHi = "सीपीयू को सुरक्षित तापमान पर रखता है। अत्यधिक गर्म होने पर नुकसान से बचने के लिए सीपीयू खुद अपनी गति धीमी कर लेता है।",
      howItWorksEn = "Thermal paste bridges microscopic air gaps between the CPU lid and copper cooler base for seamless heat conduction.",
      howItWorksBn = "থার্মাল পেস্ট সিপিইউ ঢাকনা ও ফ্যানের মাঝের সূক্ষ্ম বাতাস দূর করে তাপ সরাসরি কুলিং ফ্যানে পাঠিয়ে দেয়।",
      howItWorksHi = "थर्मल पेस्ट सीपीयू और पंखे के बीच की खाली जगह भरकर गर्मी को तुरंत बाहर निकालने में मदद करता है।",
      whyImportantEn = "A desktop or laptop with clogged dust fans will stutter and crawl slowly in games and video editing due to safety thermal throttling.",
      whyImportantBn = "ফ্যানে ধুলা জমলে কম্পিউটার অতিরিক্ত গরম হয়ে ধীরগতির হয়ে পড়ে এবং কাজের মাঝখানে হঠাৎ বন্ধ হয়ে যেতে পারে।",
      whyImportantHi = "कूलिंग पंखे में धूल जमने पर कंप्यूटर धीमा चलने लगता है और सुरक्षा कारणों से अपने आप बंद भी हो सकता है।",
      realLifeExampleEn = "Like an automobile radiator cooling a high-speed sports car engine so it doesn't overheat and seize up.",
      realLifeExampleBn = "একটি দ্রুতগামী স্পোর্টস গাড়ির ইঞ্জিনের রেডিয়েটরের মতো, যা ইঞ্জিনকে ঠান্ডা রেখে সচল রাখে।",
      realLifeExampleHi = "गाड़ी के रेडिएटर की तरह जो इंजन को ठंडा रखता है ताकि वह ज्यादा गर्म होकर खराब न हो।"
    )
  )

  val ramParts: List<RamPartDetail> = listOf(
    RamPartDetail(
      type = RamPartType.RAM_STICK,
      nameEn = "RAM Stick (DIMM Module)",
      nameBn = "র‍্যাম স্টিক (DIMM মডিউল)",
      nameHi = "रैम स्टिक (DIMM मॉड्यूल)",
      whatItIsEn = "A multi-layer printed circuit board (PCB) with gold-plated bottom connector pins that slots into the motherboard.",
      whatItIsBn = "সোনার প্রলেপযুক্ত কানেক্টর পিন বিশিষ্ট বিশেষ সার্কিট বোর্ড যা মাদারবোর্ডের স্লটে শক্তভাবে বসে থাকে।",
      whatItIsHi = "गोल्ड-प्लेटेड पिन वाला सर्किट बोर्ड जो मदरबोर्ड के खास स्लॉट में फिट होता है।",
      whatItDoesEn = "Provides temporary, ultra-fast workspace where currently open software, operating system files, and browser tabs reside.",
      whatItDoesBn = "বর্তমান চলমান সফটওয়্যার, অপারেটিং সিস্টেম ফাইল এবং ব্রাউজার ট্যাবের জন্য অতি দ্রুত অস্থায়ী কাজের জায়গা জোগায়।",
      whatItDoesHi = "वर्तमान में चल रहे सॉफ्टवेयर, ऑपरेटिंग सिस्टम और ब्राउज़र टैब के लिए सुपर-फास्ट अस्थायी कार्यक्षेत्र देता है।",
      realLifeAnalogyEn = "Your active office desk surface where you spread out books and documents you are reading right now.",
      realLifeAnalogyBn = "আপনার পড়ার বা অফিসের টেবিল, যেখানে বর্তমান কাজের প্রয়োজনীয় বই-খাতা খুলে রাখা থাকে।",
      realLifeAnalogyHi = "आपकी काम करने की मेज की तरह, जहाँ आप इस समय इस्तेमाल होने वाली किताबें और फाइलें खोलकर रखते हैं।"
    ),
    RamPartDetail(
      type = RamPartType.MEMORY_CHIPS,
      nameEn = "DRAM Memory Chips (IC)",
      nameBn = "ডি-র‍্যাম মেমরি চিপস (DRAM IC)",
      nameHi = "डी-रैम मेमोरी चिप्स (DRAM IC)",
      whatItIsEn = "Black integrated circuits containing millions of microscopic capacitor-transistor pairs that store bits as electrical charges.",
      whatItIsBn = "র‍্যামের গায়ে থাকা কালো চিপগুলো যার ভেতরে কোটি কোটি ক্ষুদ্র ক্যাপাসিটর বিদ্যুৎ চার্জ হিসেবে ডেটা ১ ও ০ আকারে ধরে রাখে।",
      whatItIsHi = "काले रंग के इंटीग्रेटेड सर्किट जिनमें लाखों कैपेसिटर बिजली के चार्ज के रूप में 1 और 0 को स्टोर करते हैं।",
      whatItDoesEn = "Holds running data. Because capacitors leak charge in milliseconds, they require constant electric refreshing (Dynamic RAM).",
      whatItDoesBn = "চলমান ডেটা জমা রাখে। এদের ক্যাপাসিটর দ্রুত চার্জ হারিয়ে ফেলে, তাই প্রতি সেকেন্ডে হাজার বার বিদ্যুৎ রিফ্রেশ করতে হয়।",
      whatItDoesHi = "डेटा स्टोर रखता है। इसके कैपेसिटर से चार्ज लीक होता रहता है, इसलिए इसे लगातार बिजली से रीफ्रेश करना पड़ता है।",
      realLifeAnalogyEn = "Like tiny cups holding water droplets that constantly need topping off to stay full.",
      realLifeAnalogyBn = "ছোট ফুটো থাকা পাত্রের মতো, যেখানে পানি ধরে রাখতে নিয়মিত সামান্য পানি ঢালতে হয়।",
      realLifeAnalogyHi = "छोटे कप की तरह जिसमें बूंद-बूंद पानी टपकता है और उसे भरा रखने के लिए बार-बार पानी डालना पड़ता है।"
    ),
    RamPartDetail(
      type = RamPartType.DIMM_SLOTS,
      nameEn = "Motherboard DIMM Slots (Dual-Channel)",
      nameBn = "মাদারবোর্ড ডিআইএমএম স্লট (ডুয়েল চ্যানেল)",
      nameHi = "मदरबोर्ड डीआईएमएम स्लॉट (डुअल चैनल)",
      whatItIsEn = "Long slotted sockets on the motherboard beside the CPU with locking latches on the ends.",
      whatItIsBn = "মাদারবোর্ডে সিপিইউ-এর ঠিক পাশে অবস্থিত লম্বা স্লট যার দুই মাথায় লক ক্লিপ থাকে।",
      whatItIsHi = "मदरबोर्ड पर सीपीयू के पास लंबे स्लॉट जिनमें दोनों तरफ लॉकिंग क्लिप्स लगी होती हैं।",
      whatItDoesEn = "Connects RAM directly to the CPU's memory controller. Dual-channel (2 sticks) doubles memory bandwidth up to 128-bit wide!",
      whatItDoesBn = "র‍্যামকে সরাসরি সিপিইউ-এর সাথে যুক্ত করে। দুটি স্টিক লাগালে (ডুয়েল চ্যানেল) ডেটা চলাচলের রাস্তা দ্বিগুণ চওড়া হয়!",
      whatItDoesHi = "रैम को सीधे सीपीयू से जोड़ता है। दो रैम स्टिक लगाने पर (डुअल चैनल) डेटा का रास्ता दोगुना चौड़ा हो जाता है!",
      realLifeAnalogyEn = "Like turning a 1-lane road into a wide 2-lane expressway for double the car traffic speed.",
      realLifeAnalogyBn = "এক লেনের রাস্তাকে দুই লেনের হাইওয়েতে রূপান্তর করার মতো যাতে ট্রাফিক দ্বিগুণ দ্রুত চলতে পারে।",
      realLifeAnalogyHi = "एक लेन की सड़क को चौड़े दो लेन वाले हाईवे में बदलने की तरह जिससे ट्रैफिक दोगुना तेज चलता है।"
    ),
    RamPartDetail(
      type = RamPartType.DATA_BUS,
      nameEn = "Memory Data Bus",
      nameBn = "মেমরি ডেটা বাস",
      nameHi = "मेमोरी डेटा बस",
      whatItIsEn = "Microscopic parallel copper traces on the motherboard connecting the RAM slots to the CPU memory controller.",
      whatItIsBn = "মাদারবোর্ডের ওপর থাকা অসংখ্য অতি সূক্ষ্ম তামার দাগ যা দিয়ে বিদ্যুৎ সংকেত আকারে ডেটা সিপিইউ-তে যাতায়াত করে।",
      whatItIsHi = "मदरबोर्ड पर बनी सूक्ष्म तांबे की लाइनें जिनके माध्यम से डेटा तेजी से सीपीयू तक जाता और आता है।",
      whatItDoesEn = "Transfers dozens of Gigabytes of data every single second between system RAM and CPU Cache.",
      whatItDoesBn = "প্রতি সেকেন্ডে বহু গিগাবাইট ডেটা নিমেষের মধ্যে র‍্যাম এবং সিপিইউ-এর মধ্যে আদান-প্রদান করে।",
      whatItDoesHi = "प्रति सेकंड दर्जनों गीगाबाइट डेटा को रैम और सीपीयू के बीच बहुत तेजी से ट्रांसफर करता है।",
      realLifeAnalogyEn = "Like a high-speed underground bullet train tunnel moving passengers back and forth between two mega cities.",
      realLifeAnalogyBn = "দুটি ব্যস্ত শহরের মধ্যে তীব্র গতিতে ছুটে চলা বুলেট ট্রেনের সুরঙ্গের মতো।",
      realLifeAnalogyHi = "दो बड़े शहरों के बीच दौड़ने वाली हाई-स्पीड बुलेट ट्रेन की तरह।"
    )
  )

  val comparisons: List<HardwareComparisonRow> = listOf(
    HardwareComparisonRow(
      featureEn = "Full Form",
      featureBn = "পূর্ণরূপ",
      featureHi = "पूरा नाम",
      cpuValueEn = "Central Processing Unit",
      cpuValueBn = "সেন্ট্রাল প্রসেসিং ইউনিট",
      cpuValueHi = "सेंट्रल प्रोसेसिंग यूनिट",
      ramValueEn = "Random Access Memory",
      ramValueBn = "র‍্যান্ডম অ্যাক্সেস মেমরি",
      ramValueHi = "रैंडम एक्सेस मेमोरी",
      romValueEn = "Read Only Memory",
      romValueBn = "রিড অনলি মেমরি",
      romValueHi = "रीड ओनली मेमोरी"
    ),
    HardwareComparisonRow(
      featureEn = "Main Purpose",
      featureBn = "প্রধান কাজ",
      featureHi = "मुख्य उद्देश्य",
      cpuValueEn = "Compute, calculate math, execute code instructions",
      cpuValueBn = "হিসাব করা, সিদ্ধান্ত নেওয়া, কোড নির্দেশ কার্যকর করা",
      cpuValueHi = "गणना करना, निर्णय लेना, सॉफ्टवेयर कोड चलाना",
      ramValueEn = "Hold active apps & OS data temporarily for fast access",
      ramValueBn = "চলমান অ্যাপ ও ওএস ডেটা অস্থায়ীভাবে দ্রুত কাজের জন্য রাখা",
      ramValueHi = "चल रहे ऐप्स और ओएस डेटा को अस्थायी रूप से तेजी से उपलब्ध कराना",
      romValueEn = "Store permanent boot firmware (BIOS/UEFI) to wake PC",
      romValueBn = "পিসি চালু করার জন্য অপরিহার্য বুট ফার্মওয়্যার (BIOS) সংরক্ষণ",
      romValueHi = "कंप्यूटर को चालू करने वाला बुनियादी बूट फर्मवेयर (BIOS) सुरक्षित रखना"
    ),
    HardwareComparisonRow(
      featureEn = "Memory Nature",
      featureBn = "স্থায়িত্বের প্রকৃতি",
      featureHi = "मेमोरी की प्रकृति",
      cpuValueEn = "Has tiny internal Registers & Cache (Volatile)",
      cpuValueBn = "ভেতরে ক্ষুদ্র রেজিস্টার ও ক্যাশ থাকে (অস্থায়ী)",
      cpuValueHi = "अंदर छोटे रजिस्टर और कैश होते हैं (अस्थायी)",
      ramValueEn = "Volatile (Data vanishes immediately without power)",
      ramValueBn = "অস্থায়ী বা ভোলাটাইল (বিদ্যুৎ চলে গেলে সব মুছে যায়)",
      ramValueHi = "अस्थायी (Volatile - बिजली बंद होते ही डेटा खत्म)",
      romValueEn = "Non-Volatile (Data stays intact forever without power)",
      romValueBn = "স্থায়ী বা নন-ভোলাটাইল (বিদ্যুৎ ছাড়াও সারাজীবন সুরক্ষিত থাকে)",
      romValueHi = "स्थायी (Non-Volatile - बिना बिजली के भी हमेशा सुरक्षित)"
    ),
    HardwareComparisonRow(
      featureEn = "Speed Level",
      featureBn = "গতির স্তর",
      featureHi = "गति का स्तर",
      cpuValueEn = "Ultra-Fast (Gigahertz / fractions of nanoseconds)",
      cpuValueBn = "অতি দ্রুত (গিগাহার্টজ / ন্যানোসেকেন্ডের ভগ্নাংশ)",
      cpuValueHi = "अत्यधिक तेज (गीगाहर्ट्ज / नैनोसेकंड के अंश)",
      ramValueEn = "Very Fast (Nanoseconds: 10–50 ns)",
      ramValueBn = "খুব দ্রুত (ন্যানোসেকেন্ড: ১০–৫০ ns)",
      ramValueHi = "बहुत तेज (10-50 नैनोसेकंड)",
      romValueEn = "Slow compared to RAM/CPU (Microseconds)",
      romValueBn = "সিপিইউ/র‍্যামের তুলনায় ধীরগতির (মাইক্রোসেকেন্ড)",
      romValueHi = "रैम और सीपीयू की तुलना में धीमी (माइक्रोसेकंड)"
    ),
    HardwareComparisonRow(
      featureEn = "Write Ability",
      featureBn = "লেখার সুবিধা",
      featureHi = "लिखने की क्षमता",
      cpuValueEn = "Processes & writes to registers and cache instantly",
      cpuValueBn = "তাত্ক্ষণিকভাবে রেজিস্টার ও ক্যাশে লিখে",
      cpuValueHi = "तुरंत गणना करके लिखता है",
      ramValueEn = "Read & Write freely millions of times a second",
      ramValueBn = "প্রতি সেকেন্ডে লক্ষ লক্ষ বার পড়া ও লেখা যায়",
      ramValueHi = "प्रति सेकंड लाखों बार आसानी से पढ़ा और लिखा जाता है",
      romValueEn = "Read-Only during normal computer operation",
      romValueBn = "সাধারণ ব্যবহারের সময় শুধুমাত্র পড়া যায় (লেখা যায় না)",
      romValueHi = "सामान्य उपयोग के दौरान केवल पढ़ा जा सकता है"
    ),
    HardwareComparisonRow(
      featureEn = "Real-Life Analogy",
      featureBn = "বাস্তব জীবনের তুলনা",
      featureHi = "वास्तविक जीवन का उदाहरण",
      cpuValueEn = "Human Brain calculating solutions",
      cpuValueBn = "মানুষের মস্তিষ্ক যা চিন্তা করে ও হিসাব করে",
      cpuValueHi = "मानव मस्तिष्क जो सोचता और गणना करता है",
      ramValueEn = "Working Desk where papers are spread out while working",
      ramValueBn = "পড়ার টেবিল যেখানে বর্তমান কাজের বই-কাগজ ছড়ানো থাকে",
      ramValueHi = "काम करने की मेज जहाँ वर्तमान काम की फाइलें खुली होती हैं",
      romValueEn = "Printed Birth Certificate or Stone Inscription",
      romValueBn = "মুদ্রিত জন্মসনদ বা পাথরে খোদাই করা স্থায়ী লিপি",
      romValueHi = "पत्थर पर खुदा लेख या जन्म प्रमाण पत्र जिसे बदला नहीं जाता"
    )
  )

  val pipelineStages: List<PipelineStageInfo> = listOf(
    PipelineStageInfo(
      stage = PipelineStage.USER_INPUT,
      stepNumber = 1,
      titleEn = "1. User Input",
      titleBn = "১. ব্যবহারকারীর নির্দেশ",
      titleHi = "1. उपयोगकर्ता का इनपुट",
      subtitleEn = "Triggering Action",
      subtitleBn = "কাজের সূচনা",
      subtitleHi = "कार्य की शुरुआत",
      descEn = "You double-click an application icon or press Enter on the keyboard.",
      descBn = "আপনি মাউস দিয়ে কোনো সফটওয়্যারের আইকনে ডাবল-ক্লিক করলেন বা কীবোর্ডে এন্টার চাপলেন।",
      descHi = "आप माउस से किसी ऐप पर डबल क्लिक करते हैं या कीबोर्ड पर एंटर दबाते हैं।",
      browserActionEn = "Double-click Chrome/Edge icon on the desktop.",
      browserActionBn = "ডেস্কটপে ক্রোম বা এজ ব্রাউজার আইকনে ডাবল ক্লিক করলেন।",
      browserActionHi = "डेस्कटॉप पर क्रोम या एज ब्राउज़र आइकन पर डबल क्लिक किया।"
    ),
    PipelineStageInfo(
      stage = PipelineStage.STORAGE,
      stepNumber = 2,
      titleEn = "2. Long-term Storage",
      titleBn = "২. স্থায়ী স্টোরেজ (SSD / HDD)",
      titleHi = "2. स्थायी स्टोरेज (SSD / HDD)",
      subtitleEn = "Permanent App Repository",
      subtitleBn = "স্থায়ী ফাইল ভাণ্ডার",
      subtitleHi = "सॉफ्टवेयर फाइलों का स्थायी घर",
      descEn = "Operating system finds the application binary files saved on your SSD or Hard Drive.",
      descBn = "অপারেটিং সিস্টেম আপনার এসএসডি বা হার্ড ড্রাইভে সংরক্ষিত মূল প্রোগ্রাম ফাইলগুলো খুঁজে বের করে।",
      descHi = "ऑपरेटिंग सिस्टम आपकी एसएसडी या हार्ड ड्राइव में सेव की गई सॉफ्टवेयर फाइलों को ढूंढता है।",
      browserActionEn = "Storage locates chrome.exe and browser program files (~500 MB).",
      browserActionBn = "এসএসডি থেকে chrome.exe এবং মূল কোড ফাইলগুলো শনাক্ত করা হয়।",
      browserActionHi = "स्टोरेज से chrome.exe और ब्राउज़र की फाइलों की पहचान होती है।"
    ),
    PipelineStageInfo(
      stage = PipelineStage.RAM,
      stepNumber = 3,
      titleEn = "3. Loaded into RAM",
      titleBn = "৩. র‍্যামে লোড হওয়া",
      titleHi = "3. रैम में लोड होना",
      subtitleEn = "Fast Working Memory",
      subtitleBn = "দ্রুত কাজের মেমরিতে স্থানান্তর",
      subtitleHi = "तेज़ कार्यशील मेमोरी में लोडिंग",
      descEn = "App files are copied from SSD into fast RAM so the CPU can access them in nanoseconds without bottleneck.",
      descBn = "ফাইলগুলো এসএসডি থেকে দ্রুতগতির র‍্যামে কপি হয় যাতে সিপিইউ কোনো বাধা ছাড়াই নিমেষে ডেটা পায়।",
      descHi = "फाइलें एसएसडी से निकलकर तेज रैम में कॉपी हो जाती हैं ताकि सीपीयू उन्हें तुरंत प्रोसेस कर सके।",
      browserActionEn = "Browser engine and your open tabs are loaded into RAM memory slots.",
      browserActionBn = "ব্রাউজার ইঞ্জিন ও আপনার প্রিয় ট্যাবগুলো র‍্যাম মেমরিতে জমা হয়।",
      browserActionHi = "ब्राउज़र इंजन और आपके खुले हुए टैब रैम में लोड हो जाते हैं।"
    ),
    PipelineStageInfo(
      stage = PipelineStage.CPU,
      stepNumber = 4,
      titleEn = "4. CPU Processing",
      titleBn = "৪. সিপিইউ প্রসেসিং",
      titleHi = "4. सीपीयू प्रोसेसिंग",
      subtitleEn = "Fetch-Decode-Execute",
      subtitleBn = "ফেচ-ডিকোড-এক্সিকিউট চক্র",
      subtitleHi = "गणना और निष्पादन",
      descEn = "CPU grabs code instructions from RAM into its Cache, decodes them with the CU, and calculates with the ALU.",
      descBn = "সিপিইউ র‍্যাম থেকে নির্দেশ ক্যাশ মেমরিতে আনে, কন্ট্রোল ইউনিট দিয়ে ডিকোড করে এবং এএলইউ দিয়ে হিসাব করে।",
      descHi = "सीपीयू रैम से निर्देशों को कैश में लाता है, समझता है और एएलयू की मदद से गणना करता है।",
      browserActionEn = "CPU compiles webpage HTML/JavaScript and decodes visual pixels.",
      browserActionBn = "সিপিইউ ওয়েবপেজের এইচটিএমএল/জাভাস্ক্রিপ্ট এবং ছবি হিসাব করে তৈরি করে।",
      browserActionHi = "सीपीयू वेबपेज के एचटीएमएल और कोड को प्रोसेस करके विज़ुअल तैयार करता है।"
    ),
    PipelineStageInfo(
      stage = PipelineStage.OUTPUT,
      stepNumber = 5,
      titleEn = "5. Output to Display",
      titleBn = "৫. ডিসপ্লেতে আউটপুট",
      titleHi = "5. डिस्प्ले पर आउटपुट",
      subtitleEn = "Visible Result",
      subtitleBn = "দৃশ্যমান ফলাফল",
      subtitleHi = "स्क्रीन पर परिणाम",
      descEn = "Processed visual frames are sent to the Graphics card and drawn instantly onto your monitor screen.",
      descBn = "হিসাব করা চূড়ান্ত ছবি গ্রাফিক্স কার্ডের মাধ্যমে সাথে সাথে আপনার মনিটরের পর্দায় ভেসে ওঠে।",
      descHi = "तैयार की गई स्क्रीन इमेज मॉनिटर पर तुरंत दिखाई देने लगती है।",
      browserActionEn = "The browser window opens smoothly on your monitor, ready for browsing!",
      browserActionBn = "মনিটরে চকচকে ব্রাউজার উইন্ডো খুলে যায় এবং আপনি ইন্টারনেট সার্ফ করতে পারেন!",
      browserActionHi = "मॉनिटर पर ब्राउज़र विंडो खुल जाती है और आप तुरंत इंटरनेट चला सकते हैं!"
    )
  )

  val quizQuestions: List<CpuRamRomQuizQuestion> = listOf(
    CpuRamRomQuizQuestion(
      id = 1,
      questionEn = "What does the abbreviation CPU stand for?",
      questionBn = "CPU এর পূর্ণরূপ কী?",
      questionHi = "CPU का पूरा नाम क्या है?",
      optionsEn = listOf(
        "Central Processing Unit",
        "Computer Personal Utility",
        "Control Program Unit",
        "Core Power Unit"
      ),
      optionsBn = listOf(
        "সেন্ট্রাল প্রসেসিং ইউনিট (Central Processing Unit)",
        "কম্পিউটার পার্সোনাল ইউটিলিটি",
        "কন্ট্রোল প্রোগ্রাম ইউনিট",
        "কোর পাওয়ার ইউনিট"
      ),
      optionsHi = listOf(
        "सेंट्रल प्रोसेसिंग यूनिट (Central Processing Unit)",
        "कंप्यूटर पर्सनल यूटिलिटी",
        "कंट्रोल प्रोग्राम यूनिट",
        "कोर पावर यूनिट"
      ),
      correctOptionIndex = 0,
      explanationEn = "CPU stands for Central Processing Unit. It acts as the primary brain of the computer that carries out instructions.",
      explanationBn = "CPU এর পূর্ণরূপ হলো Central Processing Unit। এটি কম্পিউটারের প্রধান মস্তিষ্ক হিসেবে সমস্ত কাজের নির্দেশ সম্পাদন করে।",
      explanationHi = "CPU का फुल फॉर्म Central Processing Unit है। यह कंप्यूटर का मुख्य मस्तिष्क है जो सभी निर्देशों को क्रियान्वित करता है।"
    ),
    CpuRamRomQuizQuestion(
      id = 2,
      questionEn = "Which unit inside the CPU is responsible for mathematical calculations and logical comparisons?",
      questionBn = "সিপিইউ-এর ভেতরের কোন অংশ গাণিতিক হিসাব এবং যৌক্তিক সিদ্ধান্তের কাজ করে?",
      questionHi = "सीपीयू के अंदर गणितीय गणना और तार्किक निर्णय लेने का कार्य कौन करता है?",
      optionsEn = listOf(
        "Control Unit (CU)",
        "Arithmetic Logic Unit (ALU)",
        "RAM Stick",
        "Power Supply"
      ),
      optionsBn = listOf(
        "কন্ট্রোল ইউনিট (CU)",
        "অ্যারিথমেটিক লজিক ইউনিট (ALU)",
        "র‍্যাম স্টিক",
        "পাওয়ার সাপ্লাই"
      ),
      optionsHi = listOf(
        "कंट्रोल यूनिट (CU)",
        "अंकगणित और तार्किक इकाई (ALU)",
        "रैम स्टिक",
        "पावर सप्लाई"
      ),
      correctOptionIndex = 1,
      explanationEn = "The ALU (Arithmetic Logic Unit) executes all additions, subtractions, logic comparisons (<, >, ==, AND, OR) inside the CPU.",
      explanationBn = "ALU (Arithmetic Logic Unit) যোগ, বিয়োগ এবং লজিক্যাল তুলনা করার জন্য সরাসরি দায়ী।",
      explanationHi = "ALU (Arithmetic Logic Unit) जोड़, घटाव और तुलना जैसे सभी तार्किक व गणितीय कार्य करता है।"
    ),
    CpuRamRomQuizQuestion(
      id = 3,
      questionEn = "What is the primary role of the Control Unit (CU) inside the CPU?",
      questionBn = "সিপিইউ-এর কন্ট্রোল ইউনিটের (CU) প্রধান কাজ কী?",
      questionHi = "सीपीयू के अंदर कंट्रोल यूनिट (CU) का मुख्य कार्य क्या है?",
      optionsEn = listOf(
        "Storing files permanently like photos and games",
        "Directing data traffic, fetching and decoding instructions",
        "Cooling the CPU with thermal liquid",
        "Displaying colors on the monitor"
      ),
      optionsBn = listOf(
        "ছবি ও গেমের ফাইল স্থায়ীভাবে সংরক্ষণ করা",
        "ডেটা ট্রাফিক পরিচালনা করা এবং নির্দেশ ফেচ ও ডিকোড করা",
        "লিকুইড দিয়ে সিপিইউ ঠান্ডা রাখা",
        "মনিটরে রঙ প্রদর্শন করা"
      ),
      optionsHi = listOf(
        "तस्वीरों और फाइलों को हमेशा के लिए सुरक्षित रखना",
        "डेटा ट्रैफिक को नियंत्रित करना, निर्देशों को फेच और डिकोड करना",
        "सीपीयू को ठंडा रखना",
        "मॉनिटर पर रंग प्रदर्शित करना"
      ),
      correctOptionIndex = 1,
      explanationEn = "The Control Unit acts as a traffic director: it fetches instructions from RAM, decodes them, and directs other parts when to compute.",
      explanationBn = "কন্ট্রোল ইউনিট ট্রাফিক পুলিশের মতো কাজ করে: র‍্যাম থেকে নির্দেশ আনে, ডিকোড করে এবং অন্যান্য অংশকে নির্দেশ দেয়।",
      explanationHi = "कंट्रोल यूनिट ट्रैफिक पुलिस की तरह है जो निर्देशों को लाता है, समझता है और अन्य घटकों को निर्देशित करता है।"
    ),
    CpuRamRomQuizQuestion(
      id = 4,
      questionEn = "Why is RAM called 'volatile' memory?",
      questionBn = "র‍্যাম-কে কেন 'অস্থায়ী' বা 'ভোলাটাইল' (Volatile) মেমরি বলা হয়?",
      questionHi = "रैम (RAM) को 'अस्थायी' या 'वोलेटाइल' (Volatile) मेमोरी क्यों कहा जाता है?",
      optionsEn = listOf(
        "Because it is very fragile and breaks easily",
        "Because it completely loses its stored data as soon as power is turned off",
        "Because it has moving mechanical motor parts",
        "Because it only works on mobile phones"
      ),
      optionsBn = listOf(
        "কারণ এটি কাঁচের মতো সহজেই ভেঙে যায়",
        "কারণ বিদ্যুৎ সরবরাহ বন্ধ হওয়ার সাথে সাথেই এর সমস্ত ডেটা মুছে যায়",
        "কারণ এর ভেতরে ঘূর্ণায়মান মোটর থাকে",
        "কারণ এটি কেবল মোবাইলে কাজ করে"
      ),
      optionsHi = listOf(
        "क्योंकि यह बहुत नाजुक होती है और आसानी से टूट जाती है",
        "क्योंकि बिजली बंद होते ही इसमें मौजूद सारा डेटा पूरी तरह मिट जाता है",
        "क्योंकि इसमें घूमने वाले मैकेनिकल पार्ट्स होते हैं",
        "क्योंकि यह केवल स्मार्टफोन में काम करती है"
      ),
      correctOptionIndex = 1,
      explanationEn = "RAM is volatile because its DRAM capacitors require continuous electricity to maintain charge; power cut = instant data loss.",
      explanationBn = "র‍্যামের ক্ষুদ্র ক্যাপাসিটরগুলোতে ডেটা ধরে রাখতে নিরবচ্ছিন্ন বিদ্যুতের প্রয়োজন হয়। বিদ্যুৎ চলে গেলেই ডেটা উধাও হয়ে যায়।",
      explanationHi = "रैम वोलेटाइल है क्योंकि इसके कैपेसिटर में डेटा रखने के लिए लगातार बिजली चाहिए; पावर ऑफ होते ही डेटा गायब हो जाता है।"
    ),
    CpuRamRomQuizQuestion(
      id = 5,
      questionEn = "What is the primary function of ROM in a computer?",
      questionBn = "কম্পিউটারে রমের (ROM) প্রধান কাজ কী?",
      questionHi = "कंप्यूटर में रोम (ROM) का प्राथमिक कार्य क्या है?",
      optionsEn = listOf(
        "To run modern 3D video games smoothly",
        "To store permanent boot firmware (BIOS/UEFI) that wakes up hardware",
        "To connect the mouse and keyboard wirelessly",
        "To generate sound through speakers"
      ),
      optionsBn = listOf(
        "আধুনিক ৩ডি ভিডিও গেম স্মুথভাবে চালানো",
        "স্থায়ী বুট ফার্মওয়্যার (BIOS/UEFI) সংরক্ষণ করা যা কম্পিউটার চালু করতে সাহায্য করে",
        "মাউস ও কীবোর্ড তারবিহীনভাবে যুক্ত করা",
        "স্পিকার দিয়ে শব্দ তৈরি করা"
      ),
      optionsHi = listOf(
        "भारी 3D वीडियो गेम को सुचारू रूप से चलाना",
        "स्थायी बूट फर्मवेयर (BIOS/UEFI) को स्टोर करना जो कंप्यूटर को चालू करता है",
        "वायरलेस माउस और कीबोर्ड को जोड़ना",
        "स्पीकर से आवाज़ उत्पन्न करना"
      ),
      correctOptionIndex = 1,
      explanationEn = "ROM (Read Only Memory) contains non-volatile firmware (like BIOS/UEFI) needed to test hardware and start the operating system on power-up.",
      explanationBn = "রম (Read Only Memory) স্থায়ী বায়োস/ইউইএফআই ফার্মওয়্যার সংরক্ষণ করে, যা কম্পিউটার অন করার পর হার্ডওয়্যার টেস্ট করে চালু করে।",
      explanationHi = "ROM में गैर-वाष्पशील फर्मवेयर (जैसे BIOS/UEFI) होता है जो कंप्यूटर चालू होने पर हार्डवेयर की जांच करके सिस्टम को बूट करता है।"
    ),
    CpuRamRomQuizQuestion(
      id = 6,
      questionEn = "What is CPU Cache, and why is it built directly into the processor die?",
      questionBn = "সিপিইউ ক্যাশ (Cache) মেমরি কী, এবং এটি কেন সরাসরি প্রসেসরের ভেতরে তৈরি করা হয়?",
      questionHi = "सीपीयू कैश (Cache) मेमोरी क्या है, और इसे सीधे प्रोसेसर के अंदर क्यों बनाया जाता है?",
      optionsEn = listOf(
        "It is a backup battery for emergencies",
        "It is ultra-fast on-chip SRAM that prevents the CPU from waiting on slower RAM",
        "It is software you download from the internet",
        "It is an antivirus scanner"
      ),
      optionsBn = listOf(
        "এটি জরুরি অবস্থার জন্য একটি ব্যাকআপ ব্যাটারি",
        "এটি অতি দ্রুত অন-চিপ এস-র‍্যাম যা সিপিইউ-কে ধীরগতির র‍্যামের জন্য অপেক্ষা না করিয়ে দ্রুত ডেটা দেয়",
        "এটি ইন্টারনেট থেকে ডাউনলোড করা একটি সফটওয়্যার",
        "এটি একটি অ্যান্টিভাইরাস স্ক্যানার"
      ),
      optionsHi = listOf(
        "यह आपात स्थिति के लिए एक बैकअप बैटरी है",
        "यह एक अल्ट्रा-फास्ट ऑन-चिप मेमोरी है जो सीपीयू को धीमी रैम का इंतजार करने से बचाती है",
        "यह इंटरनेट से डाउनलोड किया जाने वाला सॉफ्टवेयर है",
        "यह एक एंटीवायरस स्कैनर है"
      ),
      correctOptionIndex = 1,
      explanationEn = "CPU Cache (L1, L2, L3) sits right next to the execution cores, providing nanosecond access to frequently needed instructions.",
      explanationBn = "ক্যাশ মেমরি (L1, L2, L3) সিপিইউ কোরের ঠিক পাশে অবস্থান করে বারবার প্রয়োজন হওয়া নির্দেশগুলো ন্যানোসেকেন্ডে সরবরাহ করে।",
      explanationHi = "कैश मेमोरी सीधे कोर के पास होती है और बार-बार काम आने वाले डेटा को नैनोसेकंड में उपलब्ध कराती है।"
    ),
    CpuRamRomQuizQuestion(
      id = 7,
      questionEn = "Does a CPU with higher GHz (e.g. 4.0 GHz) always outperform one with lower GHz (e.g. 3.2 GHz)?",
      questionBn = "বেশি গিগাহার্টজ (যেমন 4.0 GHz) থাকা প্রসেসর কি সবসময়ই কম গিগাহার্টজ (যেমন 3.2 GHz) প্রসেসরের চেয়ে দ্রুত কাজ করে?",
      questionHi = "क्या अधिक गीगाहर्ट्ज़ (जैसे 4.0 GHz) वाला सीपीयू हमेशा कम गीगाहर्ट्ज़ (जैसे 3.2 GHz) वाले से तेज़ होता है?",
      optionsEn = listOf(
        "Yes, higher GHz always guarantees double the speed",
        "No, architectural efficiency, IPC (Instructions Per Cycle), and core count also determine real performance",
        "No, GHz only matters when playing music",
        "Yes, because clock speed is the only thing that matters"
      ),
      optionsBn = listOf(
        "হ্যাঁ, বেশি GHz মানেই নিশ্চিত দ্বিগুণ গতি",
        "না, আর্কিটেকচার দক্ষতা, প্রতি সাইকেলে নির্দেশের সংখ্যা (IPC) এবং কোর সংখ্যাও আসল গতি নির্ধারণ করে",
        "না, GHz শুধু গান শোনার সময় কাজে লাগে",
        "হ্যাঁ, কারণ ক্লক স্পিডই কম্পিউটারের একমাত্র পরিমাপক"
      ),
      optionsHi = listOf(
        "हाँ, अधिक GHz हमेशा तेज गति की गारंटी देता है",
        "नहीं, चिप की नई तकनीक, प्रति चक्र निर्देश (IPC) और कोर की संख्या भी वास्तविक प्रदर्शन तय करती है",
        "नहीं, GHz केवल गाने सुनने में मायने रखता है",
        "हाँ, क्योंकि क्लॉक स्पीड ही एकमात्र महत्वपूर्ण चीज़ है"
      ),
      correctOptionIndex = 1,
      explanationEn = "Clock speed (GHz) is only one factor. A newer CPU that processes 3x instructions per cycle (IPC) at 3.2 GHz easily beats an older 4.0 GHz CPU.",
      explanationBn = "শুধুমাত্র GHz দিয়ে গতি মাপা যায় না। আধুনিক আর্কিটেকচারের একটি প্রসেসর প্রতি ক্লিকে বেশি কাজ (IPC) করে কম GHz-এও অনেক দ্রুত চলে।",
      explanationHi = "गीगाहर्ट्ज़ ही सब कुछ नहीं है। आधुनिक आर्किटेक्चर वाला सीपीयू प्रति चक्र अधिक निर्देश (IPC) निष्पादित करके कम GHz पर भी पुराना सीपीयू से तेज़ होता है।"
    ),
    CpuRamRomQuizQuestion(
      id = 8,
      questionEn = "Which of the following statements correctly differentiates RAM from ROM?",
      questionBn = "নিচের কোন উক্তিটি র‍্যাম (RAM) এবং রমের (ROM) মধ্যকার পার্থক্য সঠিকভাবে তুলে ধরে?",
      questionHi = "निम्नलिखित में से कौन सा कथन रैम (RAM) और रोम (ROM) के अंतर को सही ढंग से दर्शाता है?",
      optionsEn = listOf(
        "RAM is permanent and ROM is temporary",
        "RAM is volatile working memory for apps; ROM is non-volatile permanent memory for boot firmware",
        "RAM is located inside the keyboard; ROM is inside the monitor",
        "Both lose data when the power is turned off"
      ),
      optionsBn = listOf(
        "র‍্যাম স্থায়ী এবং রম অস্থায়ী",
        "র‍্যাম হলো অ্যাপ্লিকেশনের জন্য অস্থায়ী ওয়ার্কস্পেস; রম হলো বুট ফার্মওয়্যারের জন্য স্থায়ী মেমরি",
        "র‍্যাম থাকে কীবোর্ডে এবং রম থাকে মনিটরে",
        "উভয় মেমরিই বিদ্যুৎ চলে গেলে ডেটা হারিয়ে ফেলে"
      ),
      optionsHi = listOf(
        "रैम स्थायी है और रोम अस्थायी है",
        "रैम ऐप्स के लिए अस्थायी वोलेटाइल मेमोरी है; रोम बूट फर्मवेयर के लिए स्थायी नॉन-वोलेटाइल मेमोरी है",
        "रैम कीबोर्ड के अंदर होती है और रोम मॉनिटर में",
        "बिजली बंद होने पर दोनों का डेटा नष्ट हो जाता है"
      ),
      correctOptionIndex = 1,
      explanationEn = "RAM is read/write volatile workspace that empties on shutdown. ROM is read-only non-volatile memory holding essential startup firmware.",
      explanationBn = "র‍্যাম হলো রিড-রাইট অস্থায়ী মেমরি যা শাটডাউনে মুছে যায়। রম হলো স্থায়ী রিড-অনলি মেমরি যা বুট ফার্মওয়্যার সুরক্ষিত রাখে।",
      explanationHi = "रैम री-राइटेबल अस्थायी मेमोरी है जो बंद होने पर खाली हो जाती है। रोम स्थायी मेमोरी है जो कंप्यूटर को चालू करने वाले फर्मवेयर को सुरक्षित रखती है।"
    )
  )

  fun getCpuPart(part: CpuInternalPart): CpuPartDetail {
    return cpuParts.find { it.part == part } ?: cpuParts.first()
  }

  fun getRamPart(type: RamPartType): RamPartDetail {
    return ramParts.find { it.type == type } ?: ramParts.first()
  }
}
