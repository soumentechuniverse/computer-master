package com.example.data.repository

import com.example.data.model.CommonMistake
import com.example.data.model.CourseLevel
import com.example.data.model.HowItWorksStep
import com.example.data.model.LessonDetailData
import com.example.data.model.LessonQuizQuestion
import com.example.data.model.PracticalActivity
import com.example.data.model.RealWorldExample

object ComputerBasicsLessonsPart1 {

  fun getLessons1To5(): List<LessonDetailData> {
    return listOf(
      // =================================================================
      // LESSON 1: What is a Computer?
      // =================================================================
      LessonDetailData(
        lessonId = "cb_lesson_1",
        lessonNumber = 1,
        totalLessons = 10,
        courseId = "course_basics",
        courseTitle = "Computer Basics",
        title = "What is a Computer?",
        readingTimeMinutes = 12,
        level = CourseLevel.BEGINNER,
        objectives = listOf(
          "Define what an electronic computer is and how it functions.",
          "Master the 4 core stages of computing: Input, Processing, Output, and Storage (IPOS).",
          "Identify various modern computing devices from smartphones to servers.",
          "Recognize the everyday roles of computers in modern society."
        ),
        quickIntro = "At its core, a computer is an electronic machine that accepts raw data, processes it according to logical instructions, produces meaningful output, and stores results for future use.",
        simpleExplanation = "Think of a computer like a professional chef. You provide raw ingredients (Input). The chef chops and cooks according to a recipe (Processing). The delicious meal is served on your plate (Output). Extra food and the recipe book are safely kept in the pantry (Storage).",
        detailedSections = listOf(
          "The 4 Pillars of Computing (IPOS Cycle)" to "Every computer in existence operates on the Input-Processing-Output-Storage cycle:\n\n1. Input: Getting raw data into the system (keystrokes, mouse clicks, microphone audio, camera light).\n2. Processing: The Central Processing Unit (CPU) interprets instructions and computes numbers using binary logic.\n3. Output: Presenting human-understandable information (pixels on a screen, sound waves from speakers, ink on paper).\n4. Storage: Preserving data non-volatilely on solid-state drives or hard drives so your files survive when the power is turned off.",
          "Different Shapes & Sizes of Computers" to "Computers are not only desktop boxes. Modern computing takes many shapes:\n\n• Desktops & Laptops: Powerful workstations designed for typing, office work, gaming, and creative software.\n• Smartphones & Tablets: Compact touch-controlled computers with cellular radios, GPS, and multi-core processors.\n• Embedded Systems: Microscopic computers inside microwave ovens, car braking systems, washing machines, and smart lightbulbs.\n• Servers & Supercomputers: High-density industrial machines processing web traffic, artificial intelligence models, and climate simulations.",
          "Why Computers Are Indispensable" to "Computers excel where human brains tire: they perform billions of calculations per second without fatigue, store whole libraries in chip sizes smaller than a fingernail, and communicate across continents in milliseconds."
        ),
        realWorldExamples = listOf(
          RealWorldExample(
            title = "ATM Banking Machine",
            description = "You insert your card and enter your PIN (Input), the bank computer verifies your account balance (Processing), cash is dispensed with a printed receipt (Output), and your balance updates in the database (Storage).",
            iconName = "card"
          ),
          RealWorldExample(
            title = "Smartphone Camera",
            description = "Light enters the camera sensor lens (Input), image algorithms enhance brightness and focus (Processing), the photo shows on your screen (Output), and the JPEG file is saved in flash memory (Storage).",
            iconName = "camera"
          ),
          RealWorldExample(
            title = "Digital Thermostat",
            description = "Temperature sensor reads room heat (Input), onboard chip compares it to your set goal (Processing), the air conditioner compressor triggers (Output), and energy logs are recorded (Storage).",
            iconName = "thermostat"
          )
        ),
        howItWorksSteps = listOf(
          HowItWorksStep(1, "Receiving Input", "User actions or sensor triggers are converted into electrical pulses representing binary numbers (0s and 1s)."),
          HowItWorksStep(2, "Instruction Execution", "The CPU loads the relevant program instructions from memory and processes the data through arithmetic logic."),
          HowItWorksStep(3, "Delivering Output", "Processed digital values are converted into human-perceivable signals such as visual graphics, text, audio, or tactile vibrations."),
          HowItWorksStep(4, "Long-Term Retention", "Data that must survive a restart is transferred across the motherboard bus to non-volatile persistent storage.")
        ),
        visualDiagramType = "IPO_CYCLE",
        importantPoints = listOf(
          "Computers represent all instructions, images, and text entirely as binary numbers (zeros and ones).",
          "A computer has no common sense or feelings; it strictly and blindly follows the code instructions written by programmers.",
          "The IPOS cycle (Input → Processing → Output → Storage) is the foundational blueprint for every digital system on Earth."
        ),
        commonMistakes = listOf(
          CommonMistake(
            mistake = "Only desktop towers and laptops qualify as computers.",
            correction = "Smartphones, fitness bands, automobile dashboards, and modern TVs are full computers running operating systems."
          ),
          CommonMistake(
            mistake = "Computers have their own consciousness and make independent decisions.",
            correction = "Computers only execute programmed algorithms. When an app appears intelligent, it is following calculated rules."
          )
        ),
        practicalActivity = PracticalActivity(
          title = "The Home Computer Audit",
          objective = "Identify 4 hidden embedded computers in your home environment.",
          steps = listOf(
            "Walk into your kitchen or living room and look at electronic appliances (e.g., microwave, television, smart speaker, digital watch).",
            "For each device, write down what acts as the Input (buttons, dials, microphones, sensors).",
            "Identify the Output (LED displays, beepers, lights, screens).",
            "Think about what Processing occurs (timing a cooking cycle, tuning a channel, adjusting volume)."
          )
        ),
        knowledgeCheck = LessonQuizQuestion(
          question = "What are the four fundamental stages of the universal computing cycle?",
          options = listOf(
            "Typing, Clicking, Surfing, Printing",
            "Input, Processing, Output, Storage",
            "Hardware, Software, Internet, Battery",
            "Powering, Loading, Browsing, Shutting down"
          ),
          correctOptionIndex = 1,
          explanation = "The universal computing cycle is IPOS: Input (taking data in), Processing (calculating), Output (delivering results), and Storage (saving for later)."
        ),
        prevLessonId = null,
        nextLessonId = "cb_lesson_2"
      ),

      // =================================================================
      // LESSON 2: How Does a Computer Work?
      // =================================================================
      LessonDetailData(
        lessonId = "cb_lesson_2",
        lessonNumber = 2,
        totalLessons = 10,
        courseId = "course_basics",
        courseTitle = "Computer Basics",
        title = "How Does a Computer Work?",
        readingTimeMinutes = 15,
        level = CourseLevel.BEGINNER,
        objectives = listOf(
          "Follow the journey of data through a computer system step-by-step.",
          "Understand how the CPU and RAM collaborate during active tasks.",
          "Differentiate the roles of temporary working memory and permanent storage.",
          "Trace a concrete real-world computing example from keypress to screen pixel."
        ),
        quickIntro = "When you press a key on your keyboard, a marvelous dance of electrons, memory transfers, and processor cycles happens in millionths of a second to render a letter on your screen.",
        simpleExplanation = "Think of your desk: your brain is the CPU (thinking), your open desk surface is RAM (where your active papers sit right now), and your metal filing cabinet is the hard drive (where papers are safely filed away when you leave).",
        detailedSections = listOf(
          "The Data Flow Journey" to "When you interact with a computer, data flows across high-speed electronic highways called 'buses':\n\n1. You initiate an action (like pressing the letter 'A' on your keyboard).\n2. The keyboard chip sends a digital signal interrupt to the operating system.\n3. The operating system informs the word processor app sitting in RAM.\n4. The CPU recalculates the document state and commands the graphics processor.\n5. The graphics card draws the pixel glyph 'A' onto the monitor screen.",
          "The Symbiosis of CPU & Memory" to "The CPU can process billions of instructions per second, but it cannot hold an entire movie or document inside itself. That is why RAM (Random Access Memory) acts as an ultra-fast temporary staging ground.\n\nWhen an app is launched, its code is pulled from slow permanent storage into fast RAM. The CPU then reads and writes to RAM at staggering speeds (tens of gigabytes per second).",
          "The Role of Storage in the Workflow" to "Because RAM loses all data the moment electricity is interrupted, any work you wish to preserve must be committed back into storage (SSD or HDD). That is what clicking 'Save' actually does: it copies active RAM contents onto permanent flash cells or magnetic platters."
        ),
        realWorldExamples = listOf(
          RealWorldExample(
            title = "Typing in a Word Document",
            description = "Each keypress is sent as a scancode to the CPU, stored in RAM buffer memory, rendered on screen by the display chip, and saved to disk when you press Ctrl+S.",
            iconName = "document"
          ),
          RealWorldExample(
            title = "Calculator App Math (5 + 7 = 12)",
            description = "You tap 5, +, and 7. The CPU's Arithmetic Logic Unit performs the binary addition, deposits the result '12' into a register, and draws '12' on the display.",
            iconName = "calculate"
          ),
          RealWorldExample(
            title = "Playing a Song File",
            description = "The media player reads compressed MP3 data from the SSD into RAM, the CPU decodes the audio waveform samples, and the sound card outputs analog vibrations to your speakers.",
            iconName = "music"
          )
        ),
        howItWorksSteps = listOf(
          HowItWorksStep(1, "User Input", "An input peripheral generates an electronic interrupt and sends binary code across the USB or motherboard bus."),
          HowItWorksStep(2, "Memory Allocation", "The Operating System receives the signal and updates the program state inside active Random Access Memory (RAM)."),
          HowItWorksStep(3, "Processor Execution", "The CPU fetches the machine instruction, executes the math/logic calculation, and stores the intermediate result."),
          HowItWorksStep(4, "Visual Output & Save", "The GPU draws updated pixels onto the display monitor, and the storage drive records permanent changes upon request.")
        ),
        visualDiagramType = "COMPUTER_WORKFLOW",
        importantPoints = listOf(
          "The CPU is the master processor, but it relies on RAM as its ultra-fast temporary scratchpad.",
          "Data moves through a computer along electronic circuits called system buses.",
          "Pressing 'Save' transfers your working state from volatile RAM into permanent non-volatile storage."
        ),
        commonMistakes = listOf(
          CommonMistake(
            mistake = "The CPU stores all your photos and downloaded music files.",
            correction = "The CPU has almost zero storage capacity. Your files reside on your SSD or hard drive; the CPU only processes them when loaded into RAM."
          ),
          CommonMistake(
            mistake = "Computers understand English words and spoken sentences natively.",
            correction = "Computers convert all text, sounds, and clicks into binary zeros and ones before processing."
          )
        ),
        practicalActivity = PracticalActivity(
          title = "Observing Live Computer Processing",
          objective = "Watch your computer or phone CPU and Memory respond in real-time.",
          steps = listOf(
            "On Windows, press Ctrl + Shift + Esc to open Task Manager (or on Android/Mac, open Settings > Device Care or Activity Monitor).",
            "Observe the CPU and Memory percentage graphs while your device is idling.",
            "Open a heavy application like a web browser with multiple tabs or a 3D game.",
            "Watch the CPU spike as it processes the launch instructions, and notice RAM usage increase as the app is staged into working memory."
          )
        ),
        knowledgeCheck = LessonQuizQuestion(
          question = "What happens to the data stored inside RAM when a computer is turned off completely?",
          options = listOf(
            "It is automatically saved to the cloud",
            "It remains permanently saved inside the RAM chips",
            "It is completely erased because RAM is volatile memory",
            "It converts into a PDF file"
          ),
          correctOptionIndex = 2,
          explanation = "RAM is volatile memory: it requires continuous electrical power to hold data. When power is lost, all unsaved data in RAM vanishes instantly."
        ),
        prevLessonId = "cb_lesson_1",
        nextLessonId = "cb_lesson_3"
      ),

      // =================================================================
      // LESSON 3: Hardware and Software
      // =================================================================
      LessonDetailData(
        lessonId = "cb_lesson_3",
        lessonNumber = 3,
        totalLessons = 10,
        courseId = "course_basics",
        courseTitle = "Computer Basics",
        title = "Hardware and Software",
        readingTimeMinutes = 15,
        level = CourseLevel.BEGINNER,
        objectives = listOf(
          "Understand the clear distinction between computer hardware and software.",
          "Identify System Software versus Application Software.",
          "Learn how operating systems bridge the gap between human users and silicon chips.",
          "Appreciate why hardware is useless without software, and vice versa."
        ),
        quickIntro = "A computer requires two complementary halves to exist: Hardware is the physical equipment you can touch with your hands; Software is the set of digital programs and instructions that tell the hardware what to do.",
        simpleExplanation = "Think of a grand piano and sheet music. The wooden piano, keys, strings, and pedals are the Hardware. The musical notes written on sheet music are the Software. Without sheet music, the piano is silent wood; without a piano, the notes are just silent ink on paper.",
        detailedSections = listOf(
          "What is Computer Hardware?" to "Hardware encompasses every tangible, physical component of a computer system:\n\n• Internal Hardware: Motherboard, CPU chip, RAM modules, SSD/HDD storage, power supply, and cooling fans.\n• External Hardware (Peripherals): Keyboard, mouse, monitor screen, speakers, webcams, printers, and USB flash drives.\n\nIf you can kick it, drop it, or plug it into a wall socket, it is hardware.",
          "What is Computer Software?" to "Software consists of written lines of code, algorithms, and digital data files that instruct hardware how to behave. It comes in two primary tiers:\n\n1. System Software: The foundational software that manages computer resources and hardware communication. The most famous example is the Operating System (OS) such as Microsoft Windows, macOS, Android, or Linux, alongside device drivers and firmware.\n2. Application Software: Programs designed to help users perform specific tasks, such as Google Chrome (web browsing), Microsoft Word (writing), Spotify (music), or Minecraft (gaming).",
          "The Harmony: How They Cooperate" to "When you tap an icon on your screen:\n\nUser → Taps Application Software (e.g. Photoshop) → App requests memory from System Software (Windows) → Windows commands Hardware (RAM & CPU) → Hardware executes electrical switches → Hardware displays pixels on Monitor."
        ),
        realWorldExamples = listOf(
          RealWorldExample(
            title = "Smartphone Hardware vs Apps",
            description = "The glass screen, lithium battery, and camera lenses are Hardware. The Android OS, Instagram app, and WhatsApp messenger are Software.",
            iconName = "smartphone"
          ),
          RealWorldExample(
            title = "Car Engine vs Computer Firmware",
            description = "The pistons, spark plugs, and wheels are hardware. The Engine Control Unit (ECU) software determines fuel injection timing and anti-lock braking.",
            iconName = "directions_car"
          ),
          RealWorldExample(
            title = "A Game Console",
            description = "The PlayStation or Xbox box and controllers are hardware. The game disc data and system updates are software.",
            iconName = "sports_esports"
          )
        ),
        howItWorksSteps = listOf(
          HowItWorksStep(1, "Physical Layer", "The electronic silicon chips and circuit boards stand powered on, waiting for machine language instructions."),
          HowItWorksStep(2, "System Boot", "Firmware (ROM) loads the Operating System (System Software) into active memory."),
          HowItWorksStep(3, "Application Execution", "The user launches an Application (Word, Browser), which requests CPU cycles and memory via the OS."),
          HowItWorksStep(4, "Hardware Response", "The OS translates application requests into binary electrical pulses that the physical hardware circuits execute.")
        ),
        visualDiagramType = "HARDWARE_SOFTWARE",
        importantPoints = listOf(
          "Hardware is physical and tangible; software is digital and intangible.",
          "The Operating System is the critical translator bridging user applications with physical computer silicon.",
          "Neither hardware nor software can function independently; they are symbiotic partners."
        ),
        commonMistakes = listOf(
          CommonMistake(
            mistake = "Operating Systems like Windows or Android are considered hardware because they come with the phone.",
            correction = "Operating systems are 100% digital software, specifically classified as System Software."
          ),
          CommonMistake(
            mistake = "Installing new software physically changes or damages the internal computer parts.",
            correction = "Software merely directs the flow of electricity across existing circuits; it does not change the physical hardware components."
          )
        ),
        practicalActivity = PracticalActivity(
          title = "Categorizing Your Tech Environment",
          objective = "Create a clear mental map of hardware vs software on your current device.",
          steps = listOf(
            "Look at the device you are currently holding or sitting in front of.",
            "Name 3 hardware items you can physically touch right now (e.g., glass screen, power button, keyboard).",
            "Name the System Software running your device (e.g., Android, Windows 11, iOS, macOS).",
            "Name 2 Application Software packages you used today (e.g., Computer Master, Chrome, YouTube)."
          )
        ),
        knowledgeCheck = LessonQuizQuestion(
          question = "Which of the following is an example of System Software?",
          options = listOf(
            "Microsoft Excel",
            "Google Chrome",
            "Microsoft Windows 11",
            "Spotify"
          ),
          correctOptionIndex = 2,
          explanation = "Windows 11 is an Operating System, which is System Software that manages hardware and enables other applications to run."
        ),
        prevLessonId = "cb_lesson_2",
        nextLessonId = "cb_lesson_4"
      ),

      // =================================================================
      // LESSON 4: Input Devices
      // =================================================================
      LessonDetailData(
        lessonId = "cb_lesson_4",
        lessonNumber = 4,
        totalLessons = 10,
        courseId = "course_basics",
        courseTitle = "Computer Basics",
        title = "Input Devices",
        readingTimeMinutes = 14,
        level = CourseLevel.BEGINNER,
        objectives = listOf(
          "Define what an input device is and its purpose in computing.",
          "Examine common input peripherals: keyboards, mice, touchscreens, and microphones.",
          "Understand specialized input hardware like scanners, webcams, and biometric sensors.",
          "Learn how analog human movements are converted into digital computer commands."
        ),
        quickIntro = "An input device is any piece of hardware that allows a human being (or the external environment) to feed data, instructions, or control signals into a computer.",
        simpleExplanation = "Think of input devices as the computer's five human senses: its ears (microphone), its eyes (webcam/scanner), and its sense of touch (keyboard, mouse, and touchscreen). Without them, the computer would be completely deaf and blind to the world.",
        detailedSections = listOf(
          "Primary Input Devices" to "The most common tools for direct human-to-computer interaction:\n\n• Keyboard: Uses mechanical switches or membrane contacts beneath each key. Pressing a key closes an electrical circuit, generating a unique scancode that identifies the letter or function.\n• Mouse & Trackpad: Employs an optical LED or laser sensor taking thousands of microscopic surface photos per second to detect coordinate movement across the X and Y axes.\n• Touchscreen: Uses a transparent capacitive grid covering the display that senses the minute electrical charge of human skin to calculate touch coordinates and multi-finger gestures.",
          "Audio & Visual Input Devices" to "Capturing real-world media:\n\n• Microphone: A thin flexible diaphragm vibrates with sound waves from your voice, inducing small electrical currents that an analog-to-digital converter (ADC) samples into digital audio.\n• Webcam & Digital Camera: Uses CMOS image sensors composed of millions of light-sensitive photodiode pixels, capturing color and brightness values to assemble digital photos and video frames.\n• Flatbed Scanner: Passes a high-intensity lamp and sensor bar over paper documents to convert printed text and images into digital PDF or JPEG files.",
          "Specialized & Emerging Input Peripherals" to "Modern computing leverages specialized input hardware:\n\n• Biometric Scanners: Fingerprint readers and facial recognition infrared cameras for secure authentication.\n• Barcode & QR Scanners: Optical laser decoders used in retail checkout lines and logistics tracking.\n• Game Controllers: Analog thumbsticks, triggers, and gyroscopes that translate physical hands-on gaming movements into 3D virtual coordinates."
        ),
        realWorldExamples = listOf(
          RealWorldExample(
            title = "Voice Search on Phone",
            description = "You say 'Hey Google, what is the weather?' The smartphone microphone converts acoustic sound waves into digital audio packets that the speech processor interprets.",
            iconName = "mic"
          ),
          RealWorldExample(
            title = "Pinching to Zoom a Photo",
            description = "Your thumb and index finger pull apart on glass. The capacitive touchscreen registers two moving contact points and tells the gallery app to scale up the photo.",
            iconName = "touch_app"
          ),
          RealWorldExample(
            title = "Fingerprint Device Unlock",
            description = "A capacitive sensor underneath phone glass scans the microscopic ridges of your fingertip and matches the mathematical hash against authorized templates.",
            iconName = "fingerprint"
          )
        ),
        howItWorksSteps = listOf(
          HowItWorksStep(1, "Physical Interaction", "A human presses a key, slides a mouse, speaks into a microphone, or touches a screen."),
          HowItWorksStep(2, "Sensor Detection", "Electronic or optical sensors detect the physical action (resistance change, photon absorption, or acoustic vibration)."),
          HowItWorksStep(3, "Analog-to-Digital Conversion", "Hardware controllers digitize the signal into standard binary bytes and send an interrupt signal to the OS."),
          HowItWorksStep(4, "Software Processing", "The active software receives the input event and updates the user interface or executes the commanded action.")
        ),
        visualDiagramType = "INPUT_DEVICES",
        importantPoints = listOf(
          "Input devices translate human actions into binary numbers that computer processors can understand.",
          "Modern touchscreens combine both Input (capacitive sensing) and Output (visual display) in a single physical surface.",
          "Microphones and cameras use Analog-to-Digital Converters (ADCs) to turn sound and light into digital bytes."
        ),
        commonMistakes = listOf(
          CommonMistake(
            mistake = "A computer monitor is an input device.",
            correction = "A standard monitor is purely an Output device because it only shows visuals to you. Only Touchscreen monitors serve as both Input and Output."
          ),
          CommonMistake(
            mistake = "Speakers and headphones send sound into the computer.",
            correction = "Speakers and headphones output sound to your ears. Microphones are the input devices that capture sound."
          )
        ),
        practicalActivity = PracticalActivity(
          title = "Input Device Discovery",
          objective = "Inventory every input method connected to your primary computing device.",
          steps = listOf(
            "Count how many distinct input devices your computer or phone currently has active.",
            "Check for: Keyboard, Mouse/Trackpad, Touchscreen, Front Camera, Rear Camera, Microphone, Volume buttons, Fingerprint scanner.",
            "Notice how many of these inputs you use without thinking during an average 10-minute session."
          )
        ),
        knowledgeCheck = LessonQuizQuestion(
          question = "Which of the following devices is strictly an INPUT device?",
          options = listOf(
            "Computer Monitor",
            "Inkjet Printer",
            "Microphone",
            "Stereo Headphones"
          ),
          correctOptionIndex = 2,
          explanation = "A microphone captures audio waves and inputs them into the computer as digital signals. Monitors, printers, and headphones are all output devices."
        ),
        prevLessonId = "cb_lesson_3",
        nextLessonId = "cb_lesson_5"
      ),

      // =================================================================
      // LESSON 5: Output Devices
      // =================================================================
      LessonDetailData(
        lessonId = "cb_lesson_5",
        lessonNumber = 5,
        totalLessons = 10,
        courseId = "course_basics",
        courseTitle = "Computer Basics",
        title = "Output Devices",
        readingTimeMinutes = 14,
        level = CourseLevel.BEGINNER,
        objectives = listOf(
          "Understand the purpose of output devices in communicating information to humans.",
          "Examine monitors, displays, pixel resolution, and refresh rates.",
          "Learn how audio output hardware (speakers, headphones) reproduces sound.",
          "Explore physical printers, 3D printers, projectors, and haptic devices."
        ),
        quickIntro = "An output device is any piece of computer hardware that takes processed digital data from inside the machine and converts it into a human-perceivable form, such as graphics, sounds, or printed paper.",
        simpleExplanation = "If input devices are how you speak to a computer, output devices are how the computer speaks back to you. The screen shows you what it calculated, the speakers let you hear its sounds, and the printer puts its thoughts onto paper.",
        detailedSections = listOf(
          "Monitors & Visual Displays" to "The visual centerpiece of personal computing:\n\n• Pixel Grid: Displays are made of millions of tiny microscopic color dots called pixels (Picture Elements). Each pixel contains Red, Green, and Blue sub-pixels.\n• Resolution: The total pixel count (e.g., Full HD 1920×1080 = 2 million pixels; 4K UHD 3840×2160 = over 8.2 million pixels). Higher resolution yields sharper text and photorealistic images.\n• Display Technologies: LCD (uses LED backlights shining through liquid crystals) and OLED (organic diodes where every single pixel emits its own light, achieving pitch-black contrast).",
          "Audio Output: Speakers & Headphones" to "Sound output hardware transforms binary audio data into physical pressure waves:\n\n• Digital-to-Analog Converter (DAC): The computer sound processor converts digital 0s and 1s into fluctuating electric voltages.\n• Voice Coil & Diaphragm: The electrical current flows through an electromagnet coil adjacent to a permanent magnet, pushing and pulling a flexible speaker cone back and forth thousands of times per second to create acoustic air pressure waves your ears hear as music or voices.",
          "Physical & Tactile Output" to "Tangible and projection output peripherals:\n\n• Printers: Inkjet printers spray microscopic droplets of liquid ink onto paper; Laser printers use electrostatic drums and powdered toner melted by heat rollers.\n• Projectors: High-lumen light engines beam large display images across rooms onto projection walls for classrooms and cinemas.\n• Haptic Vibration Motors: Tiny eccentric rotating mass (ERM) motors or linear resonant actuators that generate physical rumble and vibrations in smartphones and game controllers."
        ),
        realWorldExamples = listOf(
          RealWorldExample(
            title = "Watching a Movie on a 4K TV",
            description = "The streaming app decodes video bits and sends 8.3 million colored pixels per frame to the TV display 60 times every second, accompanied by multi-channel audio sent to surround sound speakers.",
            iconName = "tv"
          ),
          RealWorldExample(
            title = "Printing a Flight Boarding Pass",
            description = "A laser printer receives the document layout, electrically charges a toner drum with laser light, attracts black toner powder, and permanently fuses it to paper using 200°C heated rollers.",
            iconName = "print"
          ),
          RealWorldExample(
            title = "Phone Ringtone & Vibration",
            description = "When an incoming call arrives, the phone speaker outputs audio waves while the internal haptic motor shakes the chassis to physically alert you in your pocket.",
            iconName = "vibration"
          )
        ),
        howItWorksSteps = listOf(
          HowItWorksStep(1, "Processing Completion", "The CPU and GPU finalize the numerical values for the audio samples or screen pixel buffer."),
          HowItWorksStep(2, "Data Transmission", "Digital signals travel across display interfaces (HDMI, DisplayPort) or audio busses."),
          HowItWorksStep(3, "Signal Conversion", "DAC chips or display drivers convert raw digital binary values into analog voltages, light frequencies, or mechanical pulses."),
          HowItWorksStep(4, "Human Perception", "The user perceives the light photons (eyes), acoustic soundwaves (ears), or ink markings (touch/reading).")
        ),
        visualDiagramType = "OUTPUT_DEVICES",
        importantPoints = listOf(
          "Output devices translate electronic computer calculations into formats humans can see, hear, or touch.",
          "Display resolution (such as 1080p, 1440p, 4K) describes how many distinct pixels make up the screen canvas.",
          "A Digital-to-Analog Converter (DAC) is required to turn digital computer music files into real acoustic sound."
        ),
        commonMistakes = listOf(
          CommonMistake(
            mistake = "Higher screen resolution always makes objects physically larger.",
            correction = "Higher resolution provides more pixels in the same space, making text and images sharper and clearer, not necessarily larger."
          ),
          CommonMistake(
            mistake = "Scanners are output devices because they work closely with printers.",
            correction = "Scanners are strictly INPUT devices because they send document data into the computer. Only printers output data onto paper."
          )
        ),
        practicalActivity = PracticalActivity(
          title = "Display Resolution Check",
          objective = "Discover the exact display resolution and refresh rate of your screen.",
          steps = listOf(
            "On Windows, right-click any blank spot on your desktop and select 'Display settings' (or on Android/Mac, go to Settings > Display).",
            "Scroll down to 'Display resolution'.",
            "Note your current resolution numbers (e.g. 1920 × 1080 or 2400 × 1080).",
            "Multiply the width by the height on a calculator to see how many millions of pixels your computer draws continuously!"
          )
        ),
        knowledgeCheck = LessonQuizQuestion(
          question = "What component inside a computer converts digital sound files into analog voltages for speakers?",
          options = listOf(
            "DAC (Digital-to-Analog Converter)",
            "ADC (Analog-to-Digital Converter)",
            "ALU (Arithmetic Logic Unit)",
            "BIOS (Basic Input Output System)"
          ),
          correctOptionIndex = 0,
          explanation = "A DAC (Digital-to-Analog Converter) takes the 0s and 1s of a digital audio file and turns them into continuous electrical waves that move speaker magnets."
        ),
        prevLessonId = "cb_lesson_4",
        nextLessonId = "cb_lesson_6"
      )
    )
  }
}
