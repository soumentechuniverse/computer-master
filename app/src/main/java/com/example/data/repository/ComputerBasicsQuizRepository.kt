package com.example.data.repository

import com.example.data.model.Quiz
import com.example.data.model.QuizOption
import com.example.data.model.QuizQuestion
import com.example.data.model.QuizType

object ComputerBasicsQuizRepository {

  // -------------------------------------------------------------
  // LESSON 1: What is a Computer? (5 questions)
  // -------------------------------------------------------------
  val lesson1Quiz = Quiz(
    id = "cb_quiz_l1",
    title = "Lesson 1 Quiz: What is a Computer?",
    category = "Computer Basics",
    type = QuizType.LESSON,
    durationMinutes = 4,
    courseId = "course_comp_basics",
    courseTitle = "Computer Basics",
    lessonId = "cb_l1",
    lessonTitle = "What is a Computer?",
    questions = listOf(
      QuizQuestion(
        id = "l1_q1",
        question = "What fundamental characteristic differentiates a programmable general-purpose computer from a dedicated digital appliance like a simple electronic toaster?",
        options = listOf(
          QuizOption(0, "A computer operates purely on direct analog signals"),
          QuizOption(1, "A computer can execute varying sequences of instructions (programs) to solve diverse problems"),
          QuizOption(2, "A computer produces mechanical work directly without electrical components"),
          QuizOption(3, "A computer cannot store any information in memory")
        ),
        correctOptionIndex = 1,
        explanation = "A general-purpose computer can be programmed to perform a limitless variety of tasks by changing the software instructions it executes."
      ),
      QuizQuestion(
        id = "l1_q2",
        question = "Which sequence accurately depicts the classic Information Processing Cycle (IPOS) of modern computing?",
        options = listOf(
          QuizOption(0, "Storage → Output → Processing → Input"),
          QuizOption(1, "Processing → Input → Storage → Output"),
          QuizOption(2, "Input → Processing → Storage / Output"),
          QuizOption(3, "Output → Processing → Input → Storage")
        ),
        correctOptionIndex = 2,
        explanation = "Computers accept raw data (Input), process it into meaningful information (Processing), store it for later retrieval (Storage), and present it to users (Output)."
      ),
      QuizQuestion(
        id = "l1_q3",
        question = "Why do digital computers represent all data—numbers, text, sound, and video—internally using binary digits (0 and 1)?",
        options = listOf(
          QuizOption(0, "Binary is the only mathematical system known in electronic engineering"),
          QuizOption(1, "Two-state electronic switches (transistors being on or off) are extraordinarily reliable and noise-tolerant"),
          QuizOption(2, "Base-10 decimal systems take up more physical space on a circuit board"),
          QuizOption(3, "Binary allows computers to operate without needing an electrical power source")
        ),
        correctOptionIndex = 1,
        explanation = "Transistors naturally have two stable electrical states (high voltage / low voltage, or on / off), making binary extremely robust against electrical interference and noise."
      ),
      QuizQuestion(
        id = "l1_q4",
        question = "A smart thermostat monitors room temperature, calculates heating needs, and activates the boiler. Which category of computer is this thermostat?",
        options = listOf(
          QuizOption(0, "Supercomputer"),
          QuizOption(1, "Mainframe computer"),
          QuizOption(2, "Embedded system / dedicated computer"),
          QuizOption(3, "Workstation")
        ),
        correctOptionIndex = 2,
        explanation = "An embedded system is a microprocessor-based system designed to perform dedicated control functions within a larger mechanical or electrical system."
      ),
      QuizQuestion(
        id = "l1_q5",
        question = "How does 'data' fundamentally differ from 'information' in computing?",
        options = listOf(
          QuizOption(0, "Data is processed output, whereas information is raw unfiltered input"),
          QuizOption(1, "Data consists of raw unprocessed symbols or facts; information is organized, contextualized data that has meaning"),
          QuizOption(2, "There is no difference; the two words are interchangeable technical terms"),
          QuizOption(3, "Information is binary bits, while data is only stored in text files")
        ),
        correctOptionIndex = 1,
        explanation = "Raw numbers like '98.6, 102.4, 97.9' are data. When contextualized as 'patient body temperatures over 3 days showing a fever spike', they become meaningful information."
      )
    )
  )

  // -------------------------------------------------------------
  // LESSON 2: How Does a Computer Work? (5 questions)
  // -------------------------------------------------------------
  val lesson2Quiz = Quiz(
    id = "cb_quiz_l2",
    title = "Lesson 2 Quiz: How Does a Computer Work?",
    category = "Computer Basics",
    type = QuizType.LESSON,
    durationMinutes = 4,
    courseId = "course_comp_basics",
    courseTitle = "Computer Basics",
    lessonId = "cb_l2",
    lessonTitle = "How Does a Computer Work?",
    questions = listOf(
      QuizQuestion(
        id = "l2_q1",
        question = "What are the three core sequential phases of the CPU machine cycle?",
        options = listOf(
          QuizOption(0, "Compile, Link, and Run"),
          QuizOption(1, "Fetch, Decode, and Execute"),
          QuizOption(2, "Input, Render, and Save"),
          QuizOption(3, "Scan, Parse, and Print")
        ),
        correctOptionIndex = 1,
        explanation = "The CPU continuously fetches instructions from memory, decodes what operation needs to be performed, and executes the operation."
      ),
      QuizQuestion(
        id = "l2_q2",
        question = "During the 'Fetch' stage of the CPU instruction cycle, where does the CPU retrieve the next instruction from?",
        options = listOf(
          QuizOption(0, "Directly from the optical drive or USB flash drive"),
          QuizOption(1, "From system RAM or CPU instruction cache into an internal register"),
          QuizOption(2, "From the monitor's display buffer"),
          QuizOption(3, "From the sound card audio queue")
        ),
        correctOptionIndex = 1,
        explanation = "The Program Counter points to the next memory address in RAM/cache, and the instruction is loaded into the CPU Instruction Register."
      ),
      QuizQuestion(
        id = "l2_q3",
        question = "What is the primary role of system buses (Data Bus, Address Bus, Control Bus) inside a computer?",
        options = listOf(
          QuizOption(0, "To convert alternating current (AC) into direct current (DC) for internal circuits"),
          QuizOption(1, "To provide high-speed communication highways transferring data, addresses, and timing signals between components"),
          QuizOption(2, "To cool the CPU using pneumatic airflow channels"),
          QuizOption(3, "To format corrupted hard drives upon restart")
        ),
        correctOptionIndex = 1,
        explanation = "System buses are parallel conductive pathways that transmit data bits, memory addresses, and clock/control signals between the CPU, RAM, and chipset."
      ),
      QuizQuestion(
        id = "l2_q4",
        question = "When you click 'Save' in a text editor, what internal transition of data occurs?",
        options = listOf(
          QuizOption(0, "Data in non-volatile ROM is wiped and re-created"),
          QuizOption(1, "Data temporarily held in volatile RAM is transferred via the storage controller to persistent non-volatile storage"),
          QuizOption(2, "Data is pushed into the GPU video buffer only"),
          QuizOption(3, "Data is immediately converted into analog sound waves")
        ),
        correctOptionIndex = 1,
        explanation = "Saving transfers active work from temporary volatile RAM to permanent storage (SSD or HDD) so it survives power loss."
      ),
      QuizQuestion(
        id = "l2_q5",
        question = "What synchronizes the billion-times-a-second timing of all digital operations within the CPU?",
        options = listOf(
          QuizOption(0, "The system clock generator emitting periodic electrical pulses (measured in GHz)"),
          QuizOption(1, "The mechanical rotation speed of the case cooling fans"),
          QuizOption(2, "The network ping speed from your internet service provider"),
          QuizOption(3, "The refresh rate of the attached display monitor")
        ),
        correctOptionIndex = 0,
        explanation = "A quartz crystal clock generator produces rhythmic electrical pulses that synchronize every step of the CPU fetch-decode-execute cycle."
      )
    )
  )

  // -------------------------------------------------------------
  // LESSON 3: Hardware and Software (5 questions)
  // -------------------------------------------------------------
  val lesson3Quiz = Quiz(
    id = "cb_quiz_l3",
    title = "Lesson 3 Quiz: Hardware and Software",
    category = "Computer Basics",
    type = QuizType.LESSON,
    durationMinutes = 4,
    courseId = "course_comp_basics",
    courseTitle = "Computer Basics",
    lessonId = "cb_l3",
    lessonTitle = "Hardware and Software",
    questions = listOf(
      QuizQuestion(
        id = "l3_q1",
        question = "Which analogy best captures the complementary relationship between computer hardware and software?",
        options = listOf(
          QuizOption(0, "Hardware is the recipe book; software is the metal oven"),
          QuizOption(1, "Hardware is the physical musical instrument; software is the musical composition and sheet music telling it what notes to play"),
          QuizOption(2, "Hardware is the paint; software is the blank canvas"),
          QuizOption(3, "Hardware is electrical power; software is battery acid")
        ),
        correctOptionIndex = 1,
        explanation = "Hardware provides the tangible physical capability to perform work, while software provides the logical rules, commands, and algorithms that guide it."
      ),
      QuizQuestion(
        id = "l3_q2",
        question = "Which of the following is strictly classified as 'System Software' rather than 'Application Software'?",
        options = listOf(
          QuizOption(0, "Adobe Photoshop photo editor"),
          QuizOption(1, "Microsoft Excel spreadsheet"),
          QuizOption(2, "The Linux Operating System kernel and device drivers"),
          QuizOption(3, "Google Chrome web browser")
        ),
        correctOptionIndex = 2,
        explanation = "System software (operating systems, hardware drivers, boot firmware) manages system hardware and creates a platform for application software to run."
      ),
      QuizQuestion(
        id = "l3_q3",
        question = "What is the specific role of a 'Device Driver' in a computer system?",
        options = listOf(
          QuizOption(0, "It cleans dust off the optical lenses of laser mice"),
          QuizOption(1, "It is specialized software that translates generic OS commands into hardware-specific signals the device understands"),
          QuizOption(2, "It physically connects the power supply cable to the graphics card"),
          QuizOption(3, "It speeds up internet download bandwidth through compression")
        ),
        correctOptionIndex = 1,
        explanation = "A device driver acts as a software bridge, translating standard OS calls (like 'print this page') into the exact hardware control codes required by a specific printer or device."
      ),
      QuizQuestion(
        id = "l3_q4",
        question = "What type of software is permanently or semi-permanently flashed into non-volatile read-only hardware chips to initialize components on power-up?",
        options = listOf(
          QuizOption(0, "Shareware"),
          QuizOption(1, "Firmware (such as UEFI / BIOS)"),
          QuizOption(2, "Middleware web servers"),
          QuizOption(3, "Adware extensions")
        ),
        correctOptionIndex = 1,
        explanation = "Firmware is low-level code etched or flashed directly into non-volatile hardware chips to govern low-level hardware operation."
      ),
      QuizQuestion(
        id = "l3_q5",
        question = "If a user uninstalls a spreadsheet application, why does the computer continue to boot and function properly?",
        options = listOf(
          QuizOption(0, "Applications run in the cloud and never touch local storage"),
          QuizOption(1, "The underlying Operating System (System Software) is independent of user-level applications"),
          QuizOption(2, "Spreadsheets are stored on the motherboard's CMOS chip"),
          QuizOption(3, "Hardware recreates uninstalled software automatically")
        ),
        correctOptionIndex = 1,
        explanation = "Application software operates in user space on top of the OS; removing an application does not damage the core OS kernel or hardware operations."
      )
    )
  )

  // -------------------------------------------------------------
  // LESSON 4: Input Devices (5 questions)
  // -------------------------------------------------------------
  val lesson4Quiz = Quiz(
    id = "cb_quiz_l4",
    title = "Lesson 4 Quiz: Input Devices",
    category = "Computer Basics",
    type = QuizType.LESSON,
    durationMinutes = 4,
    courseId = "course_comp_basics",
    courseTitle = "Computer Basics",
    lessonId = "cb_l4",
    lessonTitle = "Input Devices",
    questions = listOf(
      QuizQuestion(
        id = "l4_q1",
        question = "What technical mechanism allows an optical mouse to translate physical desktop movement into smooth cursor coordinates?",
        options = listOf(
          QuizOption(0, "A mechanical rubber ball that rolls against metal encoder wheels"),
          QuizOption(1, "A tiny high-speed camera and LED/laser taking thousands of microscopic surface images per second to detect drift vectors"),
          QuizOption(2, "Magnetic induction generated by hand temperature"),
          QuizOption(3, "Sound wave reverberations bouncing off the desk surface")
        ),
        correctOptionIndex = 1,
        explanation = "Optical mice use an LED or infrared laser coupled with an optoelectronic sensor capturing thousands of microscopic surface frames per second."
      ),
      QuizQuestion(
        id = "l4_q2",
        question = "Which type of touchscreen technology supports multi-finger gestures by detecting changes in electrical charge caused by the human body?",
        options = listOf(
          QuizOption(0, "Resistive touchscreens requiring physical pressure"),
          QuizOption(1, "Capacitive touchscreens"),
          QuizOption(2, "Infrared beam interruption grids"),
          QuizOption(3, "Acoustic wave pulse recognition")
        ),
        correctOptionIndex = 1,
        explanation = "Capacitive touchscreens use the electrical conductivity of human skin to measure minute capacitance changes across an electrostatic grid."
      ),
      QuizQuestion(
        id = "l4_q3",
        question = "What component inside a microphone converts acoustic sound pressure waves into an electrical audio signal?",
        options = listOf(
          QuizOption(0, "A vibrating diaphragm coupled with a coil/magnet or capacitor plate"),
          QuizOption(1, "A quartz crystal clock multiplier"),
          QuizOption(2, "A digital-to-analog amplifier heatsink"),
          QuizOption(3, "A laser barcode diffraction grating")
        ),
        correctOptionIndex = 0,
        explanation = "Sound waves physically vibrate a flexible diaphragm, varying the electromagnetic or electrostatic field to create an analog electrical audio waveform."
      ),
      QuizQuestion(
        id = "l4_q4",
        question = "Which biometric input device evaluates unique physiological ridge patterns for high-security user authentication?",
        options = listOf(
          QuizOption(0, "Graphics tablet stylus"),
          QuizOption(1, "Fingerprint scanner"),
          QuizOption(2, "Mechanical keyboard switch"),
          QuizOption(3, "OMR (Optical Mark Reader)")
        ),
        correctOptionIndex = 1,
        explanation = "Fingerprint sensors capture optical, capacitive, or ultrasonic images of papillary friction ridges to verify individual identity."
      ),
      QuizQuestion(
        id = "l4_q5",
        question = "When typing on a keyboard, what signal does the keyboard controller send to the computer upon pressing a key?",
        options = listOf(
          QuizOption(0, "A high-voltage analog audio burst"),
          QuizOption(1, "A digital 'Scancode' identifying the exact matrix row and column of the pressed switch"),
          QuizOption(2, "An entire ASCII text paragraph"),
          QuizOption(3, "A direct instruction to turn off the display")
        ),
        correctOptionIndex = 1,
        explanation = "Keyboards send make and break scancodes indicating which physical switch closed or opened; the OS then maps this to a specific character."
      )
    )
  )

  // -------------------------------------------------------------
  // LESSON 5: Output Devices (5 questions)
  // -------------------------------------------------------------
  val lesson5Quiz = Quiz(
    id = "cb_quiz_l5",
    title = "Lesson 5 Quiz: Output Devices",
    category = "Computer Basics",
    type = QuizType.LESSON,
    durationMinutes = 4,
    courseId = "course_comp_basics",
    courseTitle = "Computer Basics",
    lessonId = "cb_l5",
    lessonTitle = "Output Devices",
    questions = listOf(
      QuizQuestion(
        id = "l5_q1",
        question = "What distinguishes an OLED display panel from a conventional LED-backlit LCD panel?",
        options = listOf(
          QuizOption(0, "OLED requires a massive white CCFL backlight behind all pixels"),
          QuizOption(1, "In OLED, each individual pixel emits its own light (organic LEDs) and can turn completely off for true infinite blacks"),
          QuizOption(2, "OLED can only produce monochrome green text"),
          QuizOption(3, "OLED screens consume equal power regardless of displayed image brightness")
        ),
        correctOptionIndex = 1,
        explanation = "OLED pixels are emissive; they emit their own light without needing a shared backlight, allowing individual pixels to shut off completely for true black."
      ),
      QuizQuestion(
        id = "l5_q2",
        question = "What does a monitor's 'Refresh Rate' (measured in Hertz, Hz) indicate?",
        options = listOf(
          QuizOption(0, "The number of color bits supported per pixel channel"),
          QuizOption(1, "The number of times per second the display redraws the screen image with new frame data"),
          QuizOption(2, "The electrical wattage consumed by the monitor during standby"),
          QuizOption(3, "The physical diagonal measurement of the screen in millimeters")
        ),
        correctOptionIndex = 1,
        explanation = "A 120Hz display refreshes its raster image 120 times every second, creating substantially smoother motion and reduced input latency."
      ),
      QuizQuestion(
        id = "l5_q3",
        question = "Which printing technology uses an electrostatic laser drum to attract powdered toner particles and permanently bonds them to paper using heated fuser rollers?",
        options = listOf(
          QuizOption(0, "Thermal receipt printer"),
          QuizOption(1, "Laser printer"),
          QuizOption(2, "Dot matrix impact printer"),
          QuizOption(3, "Piezoelectric inkjet printer")
        ),
        correctOptionIndex = 1,
        explanation = "Laser printers project an electrostatic latent image onto a photosensitive drum, pick up powdered plastic toner, and fuse it to paper with high heat and pressure."
      ),
      QuizQuestion(
        id = "l5_q4",
        question = "What hardware component translates digital audio bitstreams (0s and 1s) into continuous analog electrical waveforms that drive speaker coils?",
        options = listOf(
          QuizOption(0, "Digital-to-Analog Converter (DAC)"),
          QuizOption(1, "Analog-to-Digital Converter (ADC)"),
          QuizOption(2, "Arithmetic Logic Unit (ALU)"),
          QuizOption(3, "Direct Memory Access (DMA) channel")
        ),
        correctOptionIndex = 0,
        explanation = "The DAC (Digital-to-Analog Converter) takes numeric digital audio samples and constructs smooth continuous electrical voltages that vibrate speaker voice coils."
      ),
      QuizQuestion(
        id = "l5_q5",
        question = "What measure defines the sharpness and density of printed output on paper?",
        options = listOf(
          QuizOption(0, "Pixels Per Second (PPS)"),
          QuizOption(1, "Dots Per Inch (DPI)"),
          QuizOption(2, "Nanometers per bit (nm/b)"),
          QuizOption(3, "Rotations Per Minute (RPM)")
        ),
        correctOptionIndex = 1,
        explanation = "DPI (Dots Per Inch) measures print resolution; higher DPI values represent denser toner/ink dots per linear inch, producing sharper text and imagery."
      )
    )
  )

  // -------------------------------------------------------------
  // LESSON 6: CPU (Central Processing Unit) (5 questions)
  // -------------------------------------------------------------
  val lesson6Quiz = Quiz(
    id = "cb_quiz_l6",
    title = "Lesson 6 Quiz: CPU (Central Processing Unit)",
    category = "Computer Basics",
    type = QuizType.LESSON,
    durationMinutes = 4,
    courseId = "course_comp_basics",
    courseTitle = "Computer Basics",
    lessonId = "cb_l6",
    lessonTitle = "CPU (Central Processing Unit)",
    questions = listOf(
      QuizQuestion(
        id = "l6_q1",
        question = "Which subunit inside the CPU is responsible for carrying out arithmetic additions, subtractions, and logical comparisons (AND, OR, NOT)?",
        options = listOf(
          QuizOption(0, "Control Unit (CU)"),
          QuizOption(1, "Arithmetic Logic Unit (ALU)"),
          QuizOption(2, "Floating-point Cache Controller"),
          QuizOption(3, "Memory Management Unit (MMU)")
        ),
        correctOptionIndex = 1,
        explanation = "The Arithmetic Logic Unit (ALU) performs all basic mathematical calculations and Boolean logical comparisons within the processor."
      ),
      QuizQuestion(
        id = "l6_q2",
        question = "What role does the 'Control Unit' (CU) play within the microprocessor?",
        options = listOf(
          QuizOption(0, "It stores long-term video files and photographs"),
          QuizOption(1, "It directs the flow of data and instructions through the CPU, decoding commands and timing execution signals"),
          QuizOption(2, "It cools the silicon die by regulating thermal fan speed"),
          QuizOption(3, "It manages network IP routing packets")
        ),
        correctOptionIndex = 1,
        explanation = "The Control Unit acts as the CPU's traffic conductor, decoding instructions and signaling the ALU, registers, and memory buses when to act."
      ),
      QuizQuestion(
        id = "l6_q3",
        question = "Why does modern CPU architecture incorporate L1, L2, and L3 on-die Cache memory?",
        options = listOf(
          QuizOption(0, "To store permanent backup copies of the operating system in case of power failure"),
          QuizOption(1, "To provide ultra-fast temporary storage close to CPU execution cores, minimizing time spent waiting for slower system RAM"),
          QuizOption(2, "To eliminate the need for any storage drives in the computer"),
          QuizOption(3, "To power the motherboard RGB lighting effects")
        ),
        correctOptionIndex = 1,
        explanation = "CPU caches bridge the speed chasm between the nanosecond-speed processor core and comparatively slow main RAM, storing frequently used instructions."
      ),
      QuizQuestion(
        id = "l6_q4",
        question = "What is the key functional advantage of a multi-core processor over a single-core processor of the same clock frequency?",
        options = listOf(
          QuizOption(0, "It consumes zero electricity when active"),
          QuizOption(1, "It can execute multiple thread instructions truly simultaneously in parallel across separate execution pipelines"),
          QuizOption(2, "It multiplies internet connection download speeds by four"),
          QuizOption(3, "It eliminates the need for system RAM")
        ),
        correctOptionIndex = 1,
        explanation = "Each independent physical core possesses its own ALU, registers, and execution units, enabling true simultaneous hardware parallelism."
      ),
      QuizQuestion(
        id = "l6_q5",
        question = "What does 'Thermal Throttling' protect modern CPUs against?",
        options = listOf(
          QuizOption(0, "Operating system virus attacks"),
          QuizOption(1, "Catastrophic silicon burnout by automatically reducing clock frequency and voltage when temperatures exceed safety thresholds"),
          QuizOption(2, "Sudden internet disconnections during large downloads"),
          QuizOption(3, "Mechanical vibration of cooling fan bearings")
        ),
        correctOptionIndex = 1,
        explanation = "Thermal throttling detects dangerous temperatures (e.g. 95°C - 100°C) and downclocks the processor dynamically to prevent permanent silicon damage."
      )
    )
  )

  // -------------------------------------------------------------
  // LESSON 7: RAM (Random Access Memory) (5 questions)
  // -------------------------------------------------------------
  val lesson7Quiz = Quiz(
    id = "cb_quiz_l7",
    title = "Lesson 7 Quiz: RAM (Random Access Memory)",
    category = "Computer Basics",
    type = QuizType.LESSON,
    durationMinutes = 4,
    courseId = "course_comp_basics",
    courseTitle = "Computer Basics",
    lessonId = "cb_l7",
    lessonTitle = "RAM (Random Access Memory)",
    questions = listOf(
      QuizQuestion(
        id = "l7_q1",
        question = "Why is standard Dynamic RAM (DRAM) characterized as 'volatile' memory?",
        options = listOf(
          QuizOption(0, "Its memory chips are physically fragile and shatter easily"),
          QuizOption(1, "It loses all stored data almost immediately when electrical power is disconnected"),
          QuizOption(2, "It can only hold binary 0s but never 1s"),
          QuizOption(3, "It catches fire if subjected to high temperatures")
        ),
        correctOptionIndex = 1,
        explanation = "Volatile memory relies on continuous electrical charges in tiny capacitive cells; cutting power discharges them, wiping all contents."
      ),
      QuizQuestion(
        id = "l7_q2",
        question = "What does the 'Random' in Random Access Memory signify?",
        options = listOf(
          QuizOption(0, "Data is randomly shuffled and scrambled for military encryption"),
          QuizOption(1, "Any memory cell address can be read or written directly in constant time without sequentially scanning through preceding data"),
          QuizOption(2, "The CPU guesses random memory locations when saving files"),
          QuizOption(3, "The memory size changes randomly each day")
        ),
        correctOptionIndex = 1,
        explanation = "Unlike sequential media (like magnetic tape), RAM allows direct address referencing to any byte with equal and near-instant latency."
      ),
      QuizQuestion(
        id = "l7_q3",
        question = "What occurs when active applications demand more memory than the physical RAM capacity installed in the system?",
        options = listOf(
          QuizOption(0, "The computer automatically downloads extra physical RAM over the internet"),
          QuizOption(1, "The OS allocates 'Virtual Memory' (paging/swap file) on the SSD/HDD, slowing down performance"),
          QuizOption(2, "The monitor drops its resolution to 480p permanently"),
          QuizOption(3, "The computer power supply shuts down permanently")
        ),
        correctOptionIndex = 1,
        explanation = "When RAM runs out, the OS swaps least-recently used memory pages to a paging file on storage, leading to disk thrashing and noticeable slowdowns."
      ),
      QuizQuestion(
        id = "l7_q4",
        question = "Why do computer builders install matched RAM sticks in pairs (Dual-Channel mode) rather than a single large stick?",
        options = listOf(
          QuizOption(0, "Dual-channel memory doubles the bus width from 64-bit to 128-bit, effectively doubling memory transfer bandwidth"),
          QuizOption(1, "A single RAM stick cannot fit onto a motherboard"),
          QuizOption(2, "One stick is strictly reserved for the BIOS clock"),
          QuizOption(3, "Two sticks are required for the power switch to work")
        ),
        correctOptionIndex = 0,
        explanation = "Dual-channel architecture accesses two separate 64-bit memory channels simultaneously, doubling the maximum data throughput to the CPU."
      ),
      QuizQuestion(
        id = "l7_q5",
        question = "Which memory generation offers higher data transfer rates and lower operating voltage (1.1V) compared to DDR4?",
        options = listOf(
          QuizOption(0, "DDR2"),
          QuizOption(1, "DDR3"),
          QuizOption(2, "DDR5"),
          QuizOption(3, "SRAM 1985")
        ),
        correctOptionIndex = 2,
        explanation = "DDR5 represents the latest mainstream memory standard, operating at lower voltage (1.1V vs 1.2V DDR4) with substantially higher burst frequencies."
      )
    )
  )

  // -------------------------------------------------------------
  // LESSON 8: ROM (Read-Only Memory) (5 questions)
  // -------------------------------------------------------------
  val lesson8Quiz = Quiz(
    id = "cb_quiz_l8",
    title = "Lesson 8 Quiz: ROM (Read-Only Memory)",
    category = "Computer Basics",
    type = QuizType.LESSON,
    durationMinutes = 4,
    courseId = "course_comp_basics",
    courseTitle = "Computer Basics",
    lessonId = "cb_l8",
    lessonTitle = "ROM (Read-Only Memory)",
    questions = listOf(
      QuizQuestion(
        id = "l8_q1",
        question = "What is the primary function of the ROM chip containing BIOS/UEFI on a computer's motherboard?",
        options = listOf(
          QuizOption(0, "To store high-definition movie streams for offline viewing"),
          QuizOption(1, "To provide non-volatile boot instructions that initialize hardware and hand control over to the Operating System"),
          QuizOption(2, "To render 3D ray-traced graphics in modern video games"),
          QuizOption(3, "To recharge the laptop battery overnight")
        ),
        correctOptionIndex = 1,
        explanation = "ROM holds essential startup firmware (UEFI/BIOS) that stays intact through power cycles, executing initial hardware checks and starting the bootloader."
      ),
      QuizQuestion(
        id = "l8_q2",
        question = "What hardware diagnosis routine does UEFI/BIOS run immediately after you press the computer power button?",
        options = listOf(
          QuizOption(0, "Disk Defragmentation Check"),
          QuizOption(1, "POST (Power-On Self-Test)"),
          QuizOption(2, "Software License Validation"),
          QuizOption(3, "Antivirus Deep Scan")
        ),
        correctOptionIndex = 1,
        explanation = "POST (Power-On Self-Test) verifies that essential core hardware components—CPU, RAM, GPU, system clock—are functioning properly before booting."
      ),
      QuizQuestion(
        id = "l8_q3",
        question = "What is the essential technological difference between traditional BIOS and modern UEFI firmware?",
        options = listOf(
          QuizOption(0, "UEFI supports faster boot times, mouse GUI navigation, large drives over 2TB (GPT), and Secure Boot protection"),
          QuizOption(1, "BIOS is modern while UEFI is a legacy 16-bit system from the 1980s"),
          QuizOption(2, "UEFI requires floppy disks to boot the operating system"),
          QuizOption(3, "There is no difference; they are exact synonyms")
        ),
        correctOptionIndex = 0,
        explanation = "UEFI (Unified Extensible Firmware Interface) replaces 16-bit legacy BIOS with 64-bit architecture, Secure Boot cryptography, GPT partition support, and graphical setup."
      ),
      QuizQuestion(
        id = "l8_q4",
        question = "What is the function of the small coin-cell CMOS battery (e.g. CR2032) on desktop motherboards?",
        options = listOf(
          QuizOption(0, "It powers the CPU when the wall plug is disconnected"),
          QuizOption(1, "It supplies continuous trickle power to the Real-Time Clock (RTC) and volatile CMOS setup configuration memory"),
          QuizOption(2, "It powers the cooling fans when the computer is asleep"),
          QuizOption(3, "It increases broadband internet upload speed")
        ),
        correctOptionIndex = 1,
        explanation = "The CMOS battery keeps the Real-Time Clock ticking and preserves custom firmware settings when the computer is unplugged from wall power."
      ),
      QuizQuestion(
        id = "l8_q5",
        question = "What does 'Flashing the BIOS/UEFI' mean, and why must it be done carefully?",
        options = listOf(
          QuizOption(0, "Wiping the screen with a microfiber cloth"),
          QuizOption(1, "Rewriting the firmware on the non-volatile EEPROM chip; a power disruption mid-flash can brick the motherboard"),
          QuizOption(2, "Uninstalling video drivers to improve gaming frame rates"),
          QuizOption(3, "Increasing display backlight brightness to maximum")
        ),
        correctOptionIndex = 1,
        explanation = "Flashing overwrites low-level firmware on the motherboard's EEPROM chip; if power fails halfway through, the corrupted boot code renders the board unbootable."
      )
    )
  )

  // -------------------------------------------------------------
  // LESSON 9: Storage: HDD and SSD (5 questions)
  // -------------------------------------------------------------
  val lesson9Quiz = Quiz(
    id = "cb_quiz_l9",
    title = "Lesson 9 Quiz: Storage: HDD and SSD",
    category = "Computer Basics",
    type = QuizType.LESSON,
    durationMinutes = 4,
    courseId = "course_comp_basics",
    courseTitle = "Computer Basics",
    lessonId = "cb_l9",
    lessonTitle = "Storage: HDD and SSD",
    questions = listOf(
      QuizQuestion(
        id = "l9_q1",
        question = "Why does a Solid-State Drive (SSD) load applications and boot the operating system significantly faster than a traditional Hard Disk Drive (HDD)?",
        options = listOf(
          QuizOption(0, "SSDs use laser optical discs spinning at 15,000 RPM"),
          QuizOption(1, "SSDs use non-volatile flash memory chips with zero mechanical moving parts, yielding sub-millisecond access latencies"),
          QuizOption(2, "SSDs compress all files into 8-bit text automatically"),
          QuizOption(3, "SSDs rely solely on cloud servers to store data")
        ),
        correctOptionIndex = 1,
        explanation = "HDDs must physically sweep mechanical read/write heads across spinning magnetic platters (5–15ms latency), whereas SSD flash cells are accessed electronically in microseconds."
      ),
      QuizQuestion(
        id = "l9_q2",
        question = "Which modern SSD form-factor and protocol connects directly to high-speed PCIe motherboard lanes to achieve multi-gigabyte/sec transfer speeds?",
        options = listOf(
          QuizOption(0, "IDE / PATA ribbon cables"),
          QuizOption(1, "NVMe (Non-Volatile Memory Express) over M.2 PCIe"),
          QuizOption(2, "Floppy disk 34-pin connector"),
          QuizOption(3, "VGA storage interface")
        ),
        correctOptionIndex = 1,
        explanation = "NVMe drives communicate across PCIe bus lanes with deep command queues, far outperforming legacy SATA interfaces (which top out around ~550 MB/s)."
      ),
      QuizQuestion(
        id = "l9_q3",
        question = "What physical danger makes traditional spinning HDDs particularly susceptible to catastrophic failure when bumped or dropped while operating?",
        options = listOf(
          QuizOption(0, "Flash cell wear-leveling failure"),
          QuizOption(1, "Head crash (the read/write head flying nanometers above the platter makes physical contact and gouges the magnetic surface)"),
          QuizOption(2, "Capacitor overheating on the SATA cable"),
          QuizOption(3, "Loss of optical laser focus")
        ),
        correctOptionIndex = 1,
        explanation = "A head crash occurs when physical shock causes the actuator arm's aerodynamic slider to collide with the high-speed platter, stripping away the magnetic data layer."
      ),
      QuizQuestion(
        id = "l9_q4",
        question = "Why should you NEVER run manual defragmentation software on an SSD?",
        options = listOf(
          QuizOption(0, "SSDs do not have mechanical seek penalties, and defragmentation causes unnecessary erase/write cycles that wear out NAND flash endurance"),
          QuizOption(1, "Defragmenting an SSD accidentally deletes the Windows Operating System"),
          QuizOption(2, "Defragmenting converts an SSD into an analog HDD"),
          QuizOption(3, "SSDs can only store unfragmented files by law")
        ),
        correctOptionIndex = 0,
        explanation = "SSDs access all memory blocks at identical electronic speeds; defragmentation provides no performance benefit while needlessly consuming NAND write endurance cycles."
      ),
      QuizQuestion(
        id = "l9_q5",
        question = "What does the SSD maintenance command 'TRIM' accomplish?",
        options = listOf(
          QuizOption(0, "It trims audio files down to 30-second previews"),
          QuizOption(1, "It informs the SSD controller which data blocks are no longer valid in the OS file system, enabling proactive block erasures for sustained write speeds"),
          QuizOption(2, "It physically cuts off unused SATA power pins to save electricity"),
          QuizOption(3, "It shrinks display window borders in Windows")
        ),
        correctOptionIndex = 1,
        explanation = "TRIM enables the OS to notify the SSD controller about deleted file sectors, allowing garbage collection to consolidate and clean blocks ahead of future writes."
      )
    )
  )

  // -------------------------------------------------------------
  // LESSON 10: Computer Ports and Connectors (5 questions)
  // -------------------------------------------------------------
  val lesson10Quiz = Quiz(
    id = "cb_quiz_l10",
    title = "Lesson 10 Quiz: Ports and Connectors",
    category = "Computer Basics",
    type = QuizType.LESSON,
    durationMinutes = 4,
    courseId = "course_comp_basics",
    courseTitle = "Computer Basics",
    lessonId = "cb_l10",
    lessonTitle = "Computer Ports and Connectors",
    questions = listOf(
      QuizQuestion(
        id = "l10_q1",
        question = "Which physical feature distinguishes the reversible USB Type-C connector from legacy rectangular USB Type-A connectors?",
        options = listOf(
          QuizOption(0, "USB-C has a symmetrical 24-pin profile that plugs in either side up and supports data, high-wattage power delivery, and video output simultaneously"),
          QuizOption(1, "USB-C only carries analog sound and cannot transfer computer files"),
          QuizOption(2, "USB-C requires screw-in retention pins like VGA cables"),
          QuizOption(3, "USB-C can only be used with Apple laptops")
        ),
        correctOptionIndex = 0,
        explanation = "USB Type-C is a reversible 24-pin connector designed for universal convergence—carrying USB 3/4 data, Thunderbolt protocols, DisplayPort video, and up to 240W Power Delivery."
      ),
      QuizQuestion(
        id = "l10_q2",
        question = "What protocol standard does the blue or Lightning-bolt-labeled Thunderbolt 4 / USB4 port utilize to achieve up to 40 Gbps transfer bandwidth?",
        options = listOf(
          QuizOption(0, "Direct PCIe tunneling, DisplayPort tunneling, and USB transfer across high-speed differential pairs"),
          QuizOption(1, "Optical infrared pulses through plastic fiber strands"),
          QuizOption(2, "Modulated FM radio frequencies inside the chassis"),
          QuizOption(3, "Parallel ATA ribbon signaling")
        ),
        correctOptionIndex = 0,
        explanation = "Thunderbolt 4 / USB4 tunnels PCIe data, DisplayPort video streams, and USB packets concurrently across high-speed differential links at 40 Gbps."
      ),
      QuizQuestion(
        id = "l10_q3",
        question = "Which video connector carries high-definition uncompressed digital video AND multi-channel audio simultaneously, and is ubiquitous across home entertainment TVs and monitors?",
        options = listOf(
          QuizOption(0, "VGA (15-pin analog)"),
          QuizOption(1, "HDMI (High-Definition Multimedia Interface)"),
          QuizOption(2, "PS/2 circular connector"),
          QuizOption(3, "Serial RS-232 port")
        ),
        correctOptionIndex = 1,
        explanation = "HDMI is the global multimedia standard transmitting uncompressed digital video alongside multi-channel digital audio over a single cable."
      ),
      QuizQuestion(
        id = "l10_q4",
        question = "Which network connector standard features an 8-pin modular plug (RJ-45) attached to twisted-pair copper cables for wired gigabit local area networks?",
        options = listOf(
          QuizOption(0, "RJ-11 telephone plug"),
          QuizOption(1, "Ethernet (8P8C / RJ-45)"),
          QuizOption(2, "BNC coaxial connector"),
          QuizOption(3, "SCART connector")
        ),
        correctOptionIndex = 1,
        explanation = "Ethernet ports (standardized 8P8C / RJ-45) connect computers to routers and switches with twisted-pair copper wiring for ultra-reliable, low-latency gigabit networking."
      ),
      QuizQuestion(
        id = "l10_q5",
        question = "Why is the legacy 15-pin blue VGA port obsolete on modern computer displays?",
        options = listOf(
          QuizOption(0, "It transmits analog signals susceptible to electrical interference, blurriness, and cable degradation, while modern displays are purely digital"),
          QuizOption(1, "It is illegal under electrical safety regulations"),
          QuizOption(2, "It consumes more than 500 Watts of electricity per minute"),
          QuizOption(3, "It cannot display any colors other than blue")
        ),
        correctOptionIndex = 0,
        explanation = "VGA requires digital-to-analog and analog-to-digital conversions, causing signal attenuation, color shifting, and softness compared to native digital HDMI/DisplayPort/USB-C."
      )
    )
  )

  // -------------------------------------------------------------
  // FINAL COURSE QUIZ: Computer Basics (20 questions)
  // Balanced 2 questions per lesson across all 10 lessons
  // -------------------------------------------------------------
  val finalCourseQuiz = Quiz(
    id = "cb_quiz_final",
    title = "Computer Basics Final Certification Exam",
    category = "Computer Basics",
    type = QuizType.FINAL_EXAM,
    durationMinutes = 15,
    courseId = "course_comp_basics",
    courseTitle = "Computer Basics",
    lessonId = null,
    lessonTitle = "Comprehensive Course Final Exam",
    questions = listOf(
      // Lesson 1: What is a Computer?
      QuizQuestion(
        id = "final_q1",
        question = "According to the Von Neumann architecture and modern computer science, what are the four fundamental functions that define every computer?",
        options = listOf(
          QuizOption(0, "Input, Processing, Storage, and Output (IPOS)"),
          QuizOption(1, "Browsing, Gaming, Typing, and Printing"),
          QuizOption(2, "Cooling, Powering, Lighting, and Audio"),
          QuizOption(3, "Compiling, Linking, Testing, and Deploying")
        ),
        correctOptionIndex = 0,
        explanation = "All computer systems—from watches to supercomputers—revolve around accepting Input, Processing it via CPU, Storing state in memory/disks, and generating Output."
      ),
      QuizQuestion(
        id = "final_q2",
        question = "Why is binary (base-2) the universal mathematical foundation of digital computation rather than decimal (base-10)?",
        options = listOf(
          QuizOption(0, "Base-10 math is theoretically impossible to implement in electrical circuits"),
          QuizOption(1, "Microscopic transistors reliably distinguish between two electrical states (voltage presence / absence), minimizing circuit complexity and bit errors"),
          QuizOption(2, "Binary code produces lower heat than decimal code"),
          QuizOption(3, "Binary was mandated by international patent law")
        ),
        correctOptionIndex = 1,
        explanation = "Transistors function as simple on/off electrical switches. Determining high vs. low voltage is immensely more reliable and noise-immune than sensing ten discrete voltage levels."
      ),

      // Lesson 2: How Does a Computer Work?
      QuizQuestion(
        id = "final_q3",
        question = "During the CPU machine cycle, what specific role does the 'Instruction Register' (IR) perform?",
        options = listOf(
          QuizOption(0, "It rotates the cooling fan when the CPU heats up"),
          QuizOption(1, "It holds the current binary instruction fetched from memory while the Control Unit decodes it"),
          QuizOption(2, "It stores the user's permanent browser bookmarks"),
          QuizOption(3, "It manages network packets from the Ethernet card")
        ),
        correctOptionIndex = 1,
        explanation = "The Instruction Register temporarily holds the binary opcode retrieved from memory so the Control Unit can parse and direct its execution."
      ),
      QuizQuestion(
        id = "final_q4",
        question = "What is the primary function of the computer's 'Address Bus'?",
        options = listOf(
          QuizOption(0, "Carrying the physical memory location address that the CPU wishes to read from or write to"),
          QuizOption(1, "Carrying audio signals to the headphone jack"),
          QuizOption(2, "Delivering DC power from the power supply unit to the motherboard"),
          QuizOption(3, "Printing labels onto envelope packages")
        ),
        correctOptionIndex = 0,
        explanation = "The Address Bus is a set of wires used by the CPU to designate the exact physical location in system memory for incoming or outgoing data transfers."
      ),

      // Lesson 3: Hardware and Software
      QuizQuestion(
        id = "final_q5",
        question = "What is the fundamental functional distinction between 'System Software' and 'Application Software'?",
        options = listOf(
          QuizOption(0, "System software runs in the cloud; application software runs on desktop monitors"),
          QuizOption(1, "System software manages hardware operations and provides a baseline execution platform; application software accomplishes specific end-user tasks"),
          QuizOption(2, "Application software is made of metal; system software is digital code"),
          QuizOption(3, "System software is only found on smartphones")
        ),
        correctOptionIndex = 1,
        explanation = "Operating systems and device drivers (system software) govern hardware, memory, and devices; productivity apps (applications) solve user tasks on top of that system."
      ),
      QuizQuestion(
        id = "final_q6",
        question = "Which layer directly communicates with hardware components through low-level binary commands to shield application software from hardware idiosyncrasies?",
        options = listOf(
          QuizOption(0, "Web Browser Extensions"),
          QuizOption(1, "Device Drivers within the Operating System Kernel"),
          QuizOption(2, "User Desktop Icons"),
          QuizOption(3, "Office Productivity Suites")
        ),
        correctOptionIndex = 1,
        explanation = "Device drivers provide an abstraction layer, translating standardized OS calls into the specific electrical register writes required by proprietary hardware devices."
      ),

      // Lesson 4: Input Devices
      QuizQuestion(
        id = "final_q7",
        question = "How does an optical mouse register movement across a desk surface?",
        options = listOf(
          QuizOption(0, "By tracking continuous electrostatic charges from the user's palm"),
          QuizOption(1, "By capturing thousands of high-speed optical surface images per second with an LED/laser sensor and calculating relative drift vectors"),
          QuizOption(2, "By spinning an internal gyroscope ball against copper contact points"),
          QuizOption(3, "By receiving satellite GPS coordinates inside the room")
        ),
        correctOptionIndex = 1,
        explanation = "An optoelectronic sensor captures microscopic surface images at high frequency, using digital signal processing (DSP) to calculate relative X and Y movement."
      ),
      QuizQuestion(
        id = "final_q8",
        question = "Which sensor technology enables smartphones and laptops to verify user presence through 3D depth-mapped facial geometry?",
        options = listOf(
          QuizOption(0, "Dot matrix impact sensor"),
          QuizOption(1, "Infrared dot projector and structured light depth camera"),
          QuizOption(2, "Capacitive keyboard membrane"),
          QuizOption(3, "Magneto-optical read head")
        ),
        correctOptionIndex = 1,
        explanation = "Systems like Face ID project thousands of invisible infrared dots onto the face, reading the grid distortion with an IR camera to construct a tamper-proof 3D topological map."
      ),

      // Lesson 5: Output Devices
      QuizQuestion(
        id = "final_q9",
        question = "What primary advantage does an IPS (In-Plane Switching) LCD panel offer over a basic TN (Twisted Nematic) panel?",
        options = listOf(
          QuizOption(0, "Substantially wider viewing angles (up to 178°) and superior color accuracy with minimal color shifting"),
          QuizOption(1, "Zero electricity consumption during operation"),
          QuizOption(2, "Ability to print physical paper copies automatically"),
          QuizOption(3, "Immunity to computer viruses")
        ),
        correctOptionIndex = 0,
        explanation = "IPS liquid crystal molecules rotate parallel to the plane rather than perpendicularly, preserving vibrant color fidelity and contrast even from sharp off-axis angles."
      ),
      QuizQuestion(
        id = "final_q10",
        question = "In laser printer operation, what component fuses the plastic toner powder permanently into the paper fibers?",
        options = listOf(
          QuizOption(0, "The laser optical diode"),
          QuizOption(1, "Heated fuser rollers applying high pressure and heat (~200°C)"),
          QuizOption(2, "The corona discharge wire"),
          QuizOption(3, "The paper pickup tray roller")
        ),
        correctOptionIndex = 1,
        explanation = "The fuser assembly melts the toner particles (which contain pigment and plastic resins), pressing them permanently into the pores of the paper sheet."
      ),

      // Lesson 6: CPU
      QuizQuestion(
        id = "final_q11",
        question = "What metric represents the number of internal clock cycles a CPU core executes per second (e.g., 3.8 GHz)?",
        options = listOf(
          QuizOption(0, "Bus Width"),
          QuizOption(1, "Clock Frequency (Clock Speed)"),
          QuizOption(2, "Thermal Design Power (TDP)"),
          QuizOption(3, "Cache Latency Count")
        ),
        correctOptionIndex = 1,
        explanation = "Clock frequency, measured in gigahertz (GHz), represents billions of rhythmic synchronization pulses generated by the crystal oscillator every second."
      ),
      QuizQuestion(
        id = "final_q12",
        question = "What are the ultra-fast, tiny internal memory locations located directly inside the CPU core (such as Accumulator, Program Counter, Stack Pointer)?",
        options = listOf(
          QuizOption(0, "Flash NAND cells"),
          QuizOption(1, "CPU Registers"),
          QuizOption(2, "Virtual Swap Pages"),
          QuizOption(3, "Hard Drive Cylinders")
        ),
        correctOptionIndex = 1,
        explanation = "Registers are the fastest storage in the entire computer hierarchy, holding operands and immediate memory pointers accessible within a single CPU clock cycle."
      ),

      // Lesson 7: RAM
      QuizQuestion(
        id = "final_q13",
        question = "Why does installing 16GB of RAM make a computer feel dramatically faster during heavy multitasking compared to 4GB of RAM?",
        options = listOf(
          QuizOption(0, "It doubles the physical internet speed from the router"),
          QuizOption(1, "It allows all active programs and browser tabs to remain in high-speed RAM, preventing the OS from swapping data to slow disk virtual memory"),
          QuizOption(2, "It automatically overclocks the graphics card by 200%"),
          QuizOption(3, "It replaces the motherboard BIOS firmware")
        ),
        correctOptionIndex = 1,
        explanation = "Ample RAM allows the OS and running apps to keep all active memory pages in fast DRAM, completely eliminating sluggish virtual memory paging to storage drives."
      ),
      QuizQuestion(
        id = "final_q14",
        question = "What distinguishes Static RAM (SRAM) from Dynamic RAM (DRAM)?",
        options = listOf(
          QuizOption(0, "SRAM uses flip-flop transistor circuits that don't need continuous electrical refreshing and is much faster; DRAM uses single-transistor capacitors that must be refreshed thousands of times a second"),
          QuizOption(1, "SRAM is non-volatile while DRAM is optical"),
          QuizOption(2, "SRAM can only be read from once before being destroyed"),
          QuizOption(3, "DRAM is used strictly for CPU L1 cache")
        ),
        correctOptionIndex = 0,
        explanation = "SRAM uses 4-6 transistors per bit, making it blindingly fast for CPU caches without refresh cycles; DRAM is cheaper and denser for main memory but requires constant refresh."
      ),

      // Lesson 8: ROM
      QuizQuestion(
        id = "final_q15",
        question = "If RAM is wiped clean whenever the computer is turned off, how does the CPU know what to do the instant power is restored?",
        options = listOf(
          QuizOption(0, "It reads its initial reset vector instruction from non-volatile ROM / UEFI firmware on the motherboard"),
          QuizOption(1, "It queries the monitor over the HDMI cable for instructions"),
          QuizOption(2, "It waits for an email from the internet service provider"),
          QuizOption(3, "It randomly tries numbers until Windows opens")
        ),
        correctOptionIndex = 0,
        explanation = "At power-on, the CPU hardware is hardwired to jump to a predefined memory address (reset vector) mapped directly to the non-volatile motherboard firmware chip."
      ),
      QuizQuestion(
        id = "final_q16",
        question = "What security feature of modern UEFI firmware prevents unauthorized bootkits and rootkits from loading before the Operating System?",
        options = listOf(
          QuizOption(0, "Safe Mode Recovery"),
          QuizOption(1, "Secure Boot (verifying digital cryptographic signatures of bootloader binaries)"),
          QuizOption(2, "Dynamic Host Configuration Protocol (DHCP)"),
          QuizOption(3, "Chkdsk scanning")
        ),
        correctOptionIndex = 1,
        explanation = "Secure Boot cryptographically checks the digital signature of the OS bootloader against authorized certificates in the firmware, blocking malicious rootkits."
      ),

      // Lesson 9: Storage: HDD and SSD
      QuizQuestion(
        id = "final_q17",
        question = "What fundamental technology enables NAND flash memory in SSDs to retain stored bits even when removed from all electrical power for years?",
        options = listOf(
          QuizOption(0, "Magnetic compass alignment on spinning aluminum discs"),
          QuizOption(1, "Trapping electrical electrons inside floating-gate or charge-trap dielectric oxide insulation layers"),
          QuizOption(2, "Constant chemical battery reactions inside each chip"),
          QuizOption(3, "Mechanical microscopic levers locked into place")
        ),
        correctOptionIndex = 1,
        explanation = "NAND flash cells trap electrons inside an electrically insulated floating gate or silicon nitride charge trap, maintaining cell voltage state without external power."
      ),
      QuizQuestion(
        id = "final_q18",
        question = "Which storage performance metric measures the maximum number of independent random read/write operations a drive can execute per second?",
        options = listOf(
          QuizOption(0, "IOPS (Input/Output Operations Per Second)"),
          QuizOption(1, "Sequential Megabytes per minute"),
          QuizOption(2, "Rotational latency angle"),
          QuizOption(3, "Clock cycle period (ns)")
        ),
        correctOptionIndex = 0,
        explanation = "IOPS (Input/Output Operations Per Second) gauges random access responsiveness—crucial for OS booting and database performance, where SSDs deliver 100,000+ IOPS vs ~150 IOPS on HDDs."
      ),

      // Lesson 10: Ports and Connectors
      QuizQuestion(
        id = "final_q19",
        question = "What versatile technology allows a single USB-C cable connected to a laptop to simultaneously supply 100W of power, output 4K video to a monitor, and transfer gigabit Ethernet?",
        options = listOf(
          QuizOption(0, "USB Power Delivery (USB-PD) coupled with DisplayPort Alternate Mode (Alt Mode)"),
          QuizOption(1, "Analog audio frequency modulation"),
          QuizOption(2, "Parallel SCSI daisy chaining"),
          QuizOption(3, "VGA multi-tap splitting")
        ),
        correctOptionIndex = 0,
        explanation = "USB Type-C supports USB-PD for bi-directional high-wattage charging alongside Alternate Modes that repurpose high-speed lanes for native DisplayPort video and peripheral data."
      ),
      QuizQuestion(
        id = "final_q20",
        question = "What is the primary technical advantage of using a dedicated DisplayPort cable over an older HDMI 1.4 connection for high-end computer gaming?",
        options = listOf(
          QuizOption(0, "DisplayPort supports much higher bandwidth, multi-stream daisy-chaining (MST), and variable refresh rate standards (G-Sync / FreeSync)"),
          QuizOption(1, "DisplayPort cables do not require any metal conductors"),
          QuizOption(2, "DisplayPort increases the computer's CPU clock frequency automatically"),
          QuizOption(3, "DisplayPort eliminates the need for a dedicated graphics card")
        ),
        correctOptionIndex = 0,
        explanation = "DisplayPort is engineered for PC monitors, delivering massive packetized bandwidth capable of uncompressed high refresh rates (144Hz - 360Hz), FreeSync/G-Sync, and multi-monitor daisy chaining."
      )
    )
  )

  // -------------------------------------------------------------
  // DAILY KNOWLEDGE CHECK (5 questions)
  // Selected from Computer Basics knowledge pool
  // -------------------------------------------------------------
  val dailyKnowledgeCheck = Quiz(
    id = "cb_quiz_daily",
    title = "Daily Knowledge Check",
    category = "General Computing",
    type = QuizType.DAILY_CHECK,
    durationMinutes = 3,
    courseId = "course_comp_basics",
    courseTitle = "Computer Basics",
    lessonId = null,
    lessonTitle = "Daily Computer Knowledge Check",
    questions = listOf(
      QuizQuestion(
        id = "daily_q1",
        question = "Which internal hardware component is primarily tasked with performing all mathematical arithmetic and logical operations in a computer?",
        options = listOf(
          QuizOption(0, "RAM (Random Access Memory)"),
          QuizOption(1, "ALU (Arithmetic Logic Unit inside the CPU)"),
          QuizOption(2, "Power Supply Unit (PSU)"),
          QuizOption(3, "BIOS battery")
        ),
        correctOptionIndex = 1,
        explanation = "The Arithmetic Logic Unit (ALU) within the CPU is the computational engine carrying out additions, subtractions, and Boolean logic."
      ),
      QuizQuestion(
        id = "daily_q2",
        question = "What fundamental characteristic differentiates volatile RAM from non-volatile SSD storage?",
        options = listOf(
          QuizOption(0, "RAM loses its stored data when power is turned off; SSD storage keeps data permanently"),
          QuizOption(1, "SSD storage is volatile while RAM is permanent"),
          QuizOption(2, "RAM can only hold video files; SSD can only hold text"),
          QuizOption(3, "There is no difference in volatility between RAM and SSD")
        ),
        correctOptionIndex = 0,
        explanation = "RAM is high-speed temporary volatile memory that requires continuous electricity; SSDs use non-volatile flash cells that persist without power."
      ),
      QuizQuestion(
        id = "daily_q3",
        question = "What hardware diagnosis routine executes immediately when you press a computer's power button?",
        options = listOf(
          QuizOption(0, "Windows Registry Cleanup"),
          QuizOption(1, "POST (Power-On Self-Test) performed by UEFI/BIOS"),
          QuizOption(2, "Broadband Speed Test"),
          QuizOption(3, "Browser Cache Wipe")
        ),
        correctOptionIndex = 1,
        explanation = "POST (Power-On Self-Test) checks the presence and initial health of critical hardware (CPU, RAM, GPU, timers) before loading the operating system."
      ),
      QuizQuestion(
        id = "daily_q4",
        question = "Why does an NVMe M.2 SSD dramatically outperform a mechanical Hard Disk Drive (HDD)?",
        options = listOf(
          QuizOption(0, "NVMe SSDs access flash memory directly over PCIe lanes with zero mechanical seek time"),
          QuizOption(1, "NVMe SSDs spin at 100,000 RPM"),
          QuizOption(2, "NVMe SSDs rely solely on internet cloud servers"),
          QuizOption(3, "NVMe SSDs only store compressed black-and-white files")
        ),
        correctOptionIndex = 0,
        explanation = "Without mechanical arms or spinning platters, NVMe SSDs access flash memory in microseconds across high-speed PCIe motherboard lanes."
      ),
      QuizQuestion(
        id = "daily_q5",
        question = "Which connector allows reversible plug insertion and supports high-speed data, video, and charging over a single cable?",
        options = listOf(
          QuizOption(0, "VGA 15-pin"),
          QuizOption(1, "USB Type-C"),
          QuizOption(2, "Serial RS-232"),
          QuizOption(3, "PS/2 green port")
        ),
        correctOptionIndex = 1,
        explanation = "USB Type-C features a symmetrical, reversible 24-pin design capable of simultaneous data, Power Delivery, and DisplayPort video output."
      )
    )
  )

  // Map of all 10 lesson quizzes by lesson ID
  val lessonQuizzesByLessonId: Map<String, Quiz> = mapOf(
    "cb_l1" to lesson1Quiz,
    "cb_l2" to lesson2Quiz,
    "cb_l3" to lesson3Quiz,
    "cb_l4" to lesson4Quiz,
    "cb_l5" to lesson5Quiz,
    "cb_l6" to lesson6Quiz,
    "cb_l7" to lesson7Quiz,
    "cb_l8" to lesson8Quiz,
    "cb_l9" to lesson9Quiz,
    "cb_l10" to lesson10Quiz
  )

  // List of all 10 Computer Basics lesson quizzes in sequential order
  val allLessonQuizzes: List<Quiz> = listOf(
    lesson1Quiz,
    lesson2Quiz,
    lesson3Quiz,
    lesson4Quiz,
    lesson5Quiz,
    lesson6Quiz,
    lesson7Quiz,
    lesson8Quiz,
    lesson9Quiz,
    lesson10Quiz
  )

  fun getQuizForLesson(lessonId: String): Quiz? {
    return lessonQuizzesByLessonId[lessonId]
  }

  fun getQuizById(quizId: String): Quiz? {
    if (quizId == dailyKnowledgeCheck.id) return dailyKnowledgeCheck
    if (quizId == finalCourseQuiz.id) return finalCourseQuiz
    return allLessonQuizzes.find { it.id == quizId }
  }
}
