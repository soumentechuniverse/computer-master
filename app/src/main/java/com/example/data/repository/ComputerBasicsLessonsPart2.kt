package com.example.data.repository

import com.example.data.model.CommonMistake
import com.example.data.model.CourseLevel
import com.example.data.model.HowItWorksStep
import com.example.data.model.LessonDetailData
import com.example.data.model.LessonQuizQuestion
import com.example.data.model.PracticalActivity
import com.example.data.model.RealWorldExample

object ComputerBasicsLessonsPart2 {

  fun getLessons6To10(): List<LessonDetailData> {
    return listOf(
      // =================================================================
      // LESSON 6: CPU
      // =================================================================
      LessonDetailData(
        lessonId = "cb_lesson_6",
        lessonNumber = 6,
        totalLessons = 10,
        courseId = "course_basics",
        courseTitle = "Computer Basics",
        title = "CPU: Central Processing Unit",
        readingTimeMinutes = 18,
        level = CourseLevel.BEGINNER,
        objectives = listOf(
          "Understand what CPU stands for and why it is called the 'Brain' of the computer.",
          "Identify the three core internal sub-components: Control Unit (CU), ALU, and Registers.",
          "Learn what processor Cores, Threads, and Clock Speed (GHz) mean in practice.",
          "Master the 4-step Machine Cycle: Fetch, Decode, Execute, and Store."
        ),
        quickIntro = "The CPU (Central Processing Unit) is the primary silicon microprocessor responsible for executing instructions, performing calculations, and coordinating every other hardware component in the computer.",
        simpleExplanation = "If the computer were a bustling office, the CPU is the top executive seated at the desk: reading incoming task orders, solving mathematics problems with a calculator, directing employees where to go, and stamping approvals millions of times every single second.",
        detailedSections = listOf(
          "Anatomy of a CPU Chip" to "Inside a modern CPU package smaller than a postage stamp are billions of microscopic transistors arranged into three primary sub-units:\n\n• Control Unit (CU): The conductor of the orchestra. It extracts instructions from RAM, decodes what operation needs to be performed, and directs data traffic between CPU components and external peripherals.\n• Arithmetic Logic Unit (ALU): The mathematical calculator. It performs all fundamental arithmetic (addition, subtraction, multiplication, division) and logical comparisons (equal to, greater than, AND, OR, NOT).\n• Registers: Microscopic, blazing-fast storage pockets right inside the CPU core. They hold the immediate numbers being added or the address of the next machine instruction. Accessing a register takes less than half a nanosecond.",
          "Cores, Multi-threading & Clock Speed" to "Modern CPU performance terminology:\n\n• Multi-Core Architecture: Instead of having just one processor on the chip, modern CPUs contain 4, 8, 16, or more independent physical CPU 'cores'. Each core can execute its own separate software program simultaneously.\n• Clock Speed (Gigahertz - GHz): A CPU contains an internal quartz crystal clock that ticks billions of times per second. A 3.5 GHz processor undergoes 3.5 billion clock cycles every single second! Higher clock speed allows each individual task to execute faster.",
          "The Universal Machine Cycle" to "Every instruction a computer executes follows this 4-step loop:\n\n1. FETCH: The Control Unit grabs the next instruction address from RAM.\n2. DECODE: The CU figures out what the binary code means (e.g., 'Add Register A to Register B').\n3. EXECUTE: The ALU performs the requested calculation or data movement.\n4. STORE / WRITEBACK: The result is written back to a register or into RAM memory."
        ),
        realWorldExamples = listOf(
          RealWorldExample(
            title = "Rendering a 3D Game",
            description = "The CPU calculates player positions, physical collisions, enemy artificial intelligence, and sends polygon geometry commands to the graphics card at 120 frames per second.",
            iconName = "sports_esports"
          ),
          RealWorldExample(
            title = "Exporting a Video in 4K",
            description = "A video editor utilizes all 8 or 16 CPU cores at 100% capacity simultaneously, each core crunching color correction formulas for thousands of video frames in parallel.",
            iconName = "movie"
          ),
          RealWorldExample(
            title = "Web Browser JavaScript",
            description = "When you open an interactive webpage, the CPU fetches the website's JavaScript code and compiles it into machine instructions to animate buttons and calculate form validation.",
            iconName = "web"
          )
        ),
        howItWorksSteps = listOf(
          HowItWorksStep(1, "Instruction Fetch", "The program counter points to an instruction address in memory; the CPU fetches the instruction bytes into its internal cache."),
          HowItWorksStep(2, "Instruction Decode", "The Control Unit breaks down the binary opcode to determine whether it is an arithmetic, comparison, or memory move operation."),
          HowItWorksStep(3, "Execution by ALU", "The Arithmetic Logic Unit performs the calculation using operands stored in internal registers."),
          HowItWorksStep(4, "Writeback Result", "The completed result is saved back into a target register or sent to system RAM, and the clock counter increments.")
        ),
        visualDiagramType = "CPU_ARCHITECTURE",
        importantPoints = listOf(
          "CPU stands for Central Processing Unit; it performs the math and logic that powers all computer actions.",
          "The three main internal parts of a CPU are the Control Unit (CU), the Arithmetic Logic Unit (ALU), and the Registers.",
          "The CPU executes instructions via the 4-phase Machine Cycle: Fetch → Decode → Execute → Store.",
          "Clock speed is measured in Gigahertz (GHz), where 1 GHz represents one billion clock ticks per second."
        ),
        commonMistakes = listOf(
          CommonMistake(
            mistake = "Higher GHz clock speed always means a CPU is faster than one with lower GHz.",
            correction = "A newer CPU architecture with 8 modern cores at 3.0 GHz can vastly outperform an older single-core CPU running at 4.0 GHz due to better instructions per clock (IPC) and parallel processing."
          ),
          CommonMistake(
            mistake = "The CPU is the entire black computer box sitting under your desk.",
            correction = "The box under your desk is the computer case/chassis. The CPU is just one small silicon chip (about 4cm x 4cm) mounted onto the motherboard inside."
          )
        ),
        practicalActivity = PracticalActivity(
          title = "Check Your Device's CPU Specs",
          objective = "Discover the exact make, model, cores, and clock speed of the CPU in your current device.",
          steps = listOf(
            "On Windows: Press Ctrl + Shift + Esc, click the 'Performance' tab, and select 'CPU'.",
            "On Android: Go to Settings > About Phone (or download an app like CPU-Z).",
            "On Mac: Click Apple Menu > About This Mac.",
            "Write down: What brand is it (Intel, AMD, Qualcomm, Apple)? How many Cores does it have? What is the base speed in GHz?"
          )
        ),
        knowledgeCheck = LessonQuizQuestion(
          question = "Which internal part of the CPU is responsible for carrying out mathematical addition and logic comparisons?",
          options = listOf(
            "Control Unit (CU)",
            "Arithmetic Logic Unit (ALU)",
            "Solid State Drive (SSD)",
            "System Bus"
          ),
          correctOptionIndex = 1,
          explanation = "The ALU (Arithmetic Logic Unit) executes all mathematical calculations (+, -, *, /) and logical comparisons (AND, OR, <, >) inside the CPU."
        ),
        prevLessonId = "cb_lesson_5",
        nextLessonId = "cb_lesson_7"
      ),

      // =================================================================
      // LESSON 7: RAM
      // =================================================================
      LessonDetailData(
        lessonId = "cb_lesson_7",
        lessonNumber = 7,
        totalLessons = 10,
        courseId = "course_basics",
        courseTitle = "Computer Basics",
        title = "RAM: Random Access Memory",
        readingTimeMinutes = 16,
        level = CourseLevel.BEGINNER,
        objectives = listOf(
          "Define what RAM means and understand why it is critical for computer speed.",
          "Differentiate volatile working memory from non-volatile persistent storage.",
          "Learn how RAM enables smooth multitasking across multiple open applications.",
          "Understand memory capacities (8GB, 16GB, 32GB) and what happens when RAM fills up."
        ),
        quickIntro = "RAM (Random Access Memory) is the ultra-fast, temporary working memory where your computer keeps all the apps, browser tabs, and files that are currently open and actively in use.",
        simpleExplanation = "Imagine working on a school art project. Your desk surface is RAM: everything you place on your desk (glue, scissors, current sketch) can be grabbed in an instant. The storage closet down the hallway is your Hard Drive: it can hold thousands of items, but walking there takes much longer. When you clean up at night (power off), the desk is cleared clean.",
        detailedSections = listOf(
          "Why is RAM Needed?" to "Permanent storage drives (like SSDs and hard drives) are great for preserving terabytes of files, but they are relatively slow compared to the blazing speed of the CPU.\n\nIf the CPU had to fetch every single instruction directly from an SSD, your computer would crawl at a frustrating snail's pace. RAM bridges this speed chasm: it can read and write data at over 50,000 Megabytes per second with near-instant access latency measured in nanoseconds.",
          "Volatile vs Non-Volatile Memory" to "The most defining characteristic of RAM is that it is 'Volatile Memory':\n\n• Requires Continuous Electricity: Each memory cell in RAM uses tiny microscopic capacitors and transistors that maintain an electrical charge.\n• Power Loss Wipes Everything: The moment you turn off your PC, restart your phone, or suffer a power outage, every single byte held in RAM evaporates completely.\n• This is why autosave and manual 'Save' buttons exist: saving writes your RAM contents onto non-volatile permanent storage.",
          "RAM & Multitasking Capacity" to "How much RAM do you need in a modern computer?\n\n• 8 GB: The bare minimum for modern web browsing, office documents, and light streaming.\n• 16 GB: The sweet spot for modern PCs, providing silky smooth multitasking with 30+ browser tabs, Spotify, and office suites running simultaneously.\n• 32 GB – 64 GB: Ideal for power users, software developers, 4K video editing, virtual machines, and heavy 3D gaming.\n\nWhat happens when RAM runs out? The operating system uses 'Virtual Memory' (paging), swapping inactive RAM blocks onto your slower storage drive, causing sluggishness and stutter."
        ),
        realWorldExamples = listOf(
          RealWorldExample(
            title = "Opening 30 Chrome Tabs",
            description = "Each website tab stores its HTML code, images, and script state inside RAM. With 16GB of RAM, all tabs switch instantly without reloading from the internet.",
            iconName = "tab"
          ),
          RealWorldExample(
            title = "Playing an Open-World Game",
            description = "When the game loads a level, 3D character models, textures, and sound files are copied from your SSD into 12GB of RAM for lag-free, stutter-free gameplay.",
            iconName = "sports_esports"
          ),
          RealWorldExample(
            title = "Switching Between Phone Apps",
            description = "When you switch from Instagram to WhatsApp and back, the app stays instantly in the exact same spot because its state was paused in phone RAM.",
            iconName = "swap_horiz"
          )
        ),
        howItWorksSteps = listOf(
          HowItWorksStep(1, "App Launch", "You double-click an application icon; the OS commands the SSD to load executable binary data into available RAM addresses."),
          HowItWorksStep(2, "Instant CPU Access", "The CPU reads and modifies active variables inside RAM across the multi-channel memory bus at tens of gigabytes per second."),
          HowItWorksStep(3, "Multitasking Juggling", "Multiple apps share distinct allocated RAM blocks protected by the Operating System's memory management unit."),
          HowItWorksStep(4, "Memory Deallocation", "When you close an application, the OS marks those RAM addresses as free and available for other programs to use.")
        ),
        visualDiagramType = "RAM_HIERARCHY",
        importantPoints = listOf(
          "RAM stands for Random Access Memory; 'Random' means any memory cell can be accessed directly just as quickly as any other.",
          "RAM is volatile: all unsaved data is instantly lost when electrical power is cut.",
          "RAM does not store your files permanently; it is a temporary high-speed workspace for active applications.",
          "Having sufficient RAM prevents your system from freezing or slowing down when multiple programs run at once."
        ),
        commonMistakes = listOf(
          CommonMistake(
            mistake = "Buying more RAM will make your internet connection faster.",
            correction = "RAM only improves local multitasking and program loading. It has zero effect on your home Wi-Fi or broadband internet speed."
          ),
          CommonMistake(
            mistake = "Having 512GB of SSD storage means you have 512GB of RAM.",
            correction = "Storage capacity (e.g. 512GB SSD) and RAM capacity (e.g. 8GB or 16GB RAM) are two totally different components with vastly different purposes."
          )
        ),
        practicalActivity = PracticalActivity(
          title = "Inspect Your Real-Time RAM Usage",
          objective = "See how much RAM your system possesses and how much is actively consumed.",
          steps = listOf(
            "Open Task Manager on Windows (Ctrl + Shift + Esc) and click the 'Performance' tab > 'Memory'.",
            "On Android: Go to Settings > System > Developer Options > Memory (or Device Care > RAM).",
            "Note: Total Installed RAM (e.g. 8GB, 16GB) and In-Use Memory (e.g. 6.2 GB).",
            "Open 5 new web browser tabs and watch the 'In-Use Memory' number climb in real-time!"
          )
        ),
        knowledgeCheck = LessonQuizQuestion(
          question = "Why is RAM referred to as 'volatile' memory?",
          options = listOf(
            "Because it can catch fire if overloaded",
            "Because its data is completely erased when electrical power is disconnected",
            "Because it changes your files permanently without asking",
            "Because it can only store video games"
          ),
          correctOptionIndex = 1,
          explanation = "Volatile memory requires continuous electrical current to retain its state. When power is shut off, all information in RAM disappears completely."
        ),
        prevLessonId = "cb_lesson_6",
        nextLessonId = "cb_lesson_8"
      ),

      // =================================================================
      // LESSON 8: ROM
      // =================================================================
      LessonDetailData(
        lessonId = "cb_lesson_8",
        lessonNumber = 8,
        totalLessons = 10,
        courseId = "course_basics",
        courseTitle = "Computer Basics",
        title = "ROM: Read-Only Memory",
        readingTimeMinutes = 14,
        level = CourseLevel.BEGINNER,
        objectives = listOf(
          "Understand what ROM means and why modern computers cannot start without it.",
          "Differentiate non-volatile ROM from volatile RAM.",
          "Learn what Firmware, BIOS, and modern UEFI mean.",
          "Identify everyday hardware examples containing dedicated ROM microchips."
        ),
        quickIntro = "ROM (Read-Only Memory) is permanent, non-volatile computer memory that holds the essential bootstrap instructions needed to awaken the computer hardware and boot the operating system when you press the power button.",
        simpleExplanation = "Think of ROM as a human baby's natural instincts: a newborn doesn't need to be taught how to breathe or pump blood—those instructions are permanently hardwired into DNA from birth. Similarly, ROM is the hardwired silicon instinct that teaches a computer how to wake up before it even knows Windows or Android exists.",
        detailedSections = listOf(
          "The Purpose of ROM" to "When a computer is powered off, RAM is completely blank. The CPU has amnesia: it doesn't know what keyboard is attached, where the SSD is located, or what operating system to boot.\n\nThis is called the 'Chicken-and-Egg' dilemma of computing: how does a computer load software when it has no software in memory to begin with? The answer is ROM. A small chip soldered directly onto the motherboard contains permanent startup code called UEFI/BIOS that runs the moment electricity reaches the board.",
          "Non-Volatile Memory & Firmware" to "Key attributes of ROM:\n\n• Non-Volatile: It retains its data permanently, with or without electricity. You could leave a motherboard in a closet for 10 years, and its ROM code will remain intact.\n• Read-Mostly: In standard operation, the CPU only reads instructions from ROM and cannot accidentally overwrite or corrupt them with ordinary user software.\n• Firmware: The special category of low-level software stored inside ROM chips. It directly interfaces with the bare silicon hardware circuits.",
          "The Boot Process (POST)" to "What happens in the first 2 seconds of turning on a computer?\n\n1. Power-On: Electricity energizes the motherboard.\n2. ROM UEFI/BIOS Takes Charge: The CPU starts reading code at the pre-configured memory address in ROM.\n3. POST (Power-On Self-Test): ROM checks whether RAM is present, tests the CPU health, and verifies the graphics card.\n4. OS Hand-off: Once hardware checks pass, ROM finds your SSD, loads Windows/Linux/Android into RAM, and hands over control."
        ),
        realWorldExamples = listOf(
          RealWorldExample(
            title = "Motherboard UEFI/BIOS Chip",
            description = "The small 8-pin SPI flash ROM chip on your motherboard that boots your PC and lets you configure fan curves, hardware voltages, and boot drive priority.",
            iconName = "memory"
          ),
          RealWorldExample(
            title = "Wi-Fi Router Firmware",
            description = "Your home Wi-Fi router boots its Linux networking stack from an onboard ROM flash chip every time you plug it into the wall outlet.",
            iconName = "router"
          ),
          RealWorldExample(
            title = "Microwave Oven Controller",
            description = "The electronic chip inside your microwave runs its permanent timer, defrost cycles, and keypad controls from embedded ROM that can never be modified.",
            iconName = "microwave"
          )
        ),
        howItWorksSteps = listOf(
          HowItWorksStep(1, "Power Switch Closed", "Electrical power flows from the power supply unit into the motherboard circuits."),
          HowItWorksStep(2, "Reset Vector Jump", "The CPU automatically jumps to a hardcoded memory location pointing to the ROM firmware chip."),
          HowItWorksStep(3, "Hardware POST Check", "The firmware tests all vital organs: CPU registers, RAM integrity, GPU initialization, and keyboard detection."),
          HowItWorksStep(4, "Bootloader Execution", "The firmware locates the Operating System boot partition on your SSD, copies it into RAM, and launches it.")
        ),
        visualDiagramType = "ROM_FIRMWARE",
        importantPoints = listOf(
          "ROM stands for Read-Only Memory; its contents are permanent and non-volatile.",
          "ROM contains the BIOS (Basic Input/Output System) or modern UEFI firmware required to start up a computer.",
          "During startup, ROM executes the POST (Power-On Self-Test) to ensure all hardware is functional before booting the OS.",
          "Unlike RAM (which is erased upon shutdown), ROM retains its code forever without battery power."
        ),
        commonMistakes = listOf(
          CommonMistake(
            mistake = "ROM and RAM are interchangeable names for computer memory.",
            correction = "RAM is temporary, fast, read-write working memory. ROM is permanent, non-volatile startup firmware code."
          ),
          CommonMistake(
            mistake = "You can download more ROM from the internet.",
            correction = "ROM is a physical silicon chip soldered onto your motherboard. While firmware can sometimes be updated ('flashed'), you cannot download physical memory chips."
          )
        ),
        practicalActivity = PracticalActivity(
          title = "Identify Your Device's BIOS/UEFI Version",
          objective = "Find the exact firmware version installed in your computer's ROM chip.",
          steps = listOf(
            "On Windows: Press Win + R, type 'msinfo32', and press Enter to open System Information.",
            "Look for the row named 'BIOS Version/Date' in the summary table.",
            "Write down the manufacturer (e.g. American Megatrends, Insyde, Dell, HP) and the version number.",
            "Notice how this firmware is tied directly to your physical motherboard model!"
          )
        ),
        knowledgeCheck = LessonQuizQuestion(
          question = "What critical diagnostic test does computer ROM execute immediately upon powering on?",
          options = listOf(
            "POST (Power-On Self-Test)",
            "Antivirus Malware Scan",
            "Internet Speed Benchmark",
            "Hard Drive Defragmentation"
          ),
          correctOptionIndex = 0,
          explanation = "POST (Power-On Self-Test) is the essential diagnostic routine run by ROM to verify that the CPU, RAM, and essential hardware are operating properly before launching the OS."
        ),
        prevLessonId = "cb_lesson_7",
        nextLessonId = "cb_lesson_9"
      ),

      // =================================================================
      // LESSON 9: Storage: HDD and SSD
      // =================================================================
      LessonDetailData(
        lessonId = "cb_lesson_9",
        lessonNumber = 9,
        totalLessons = 10,
        courseId = "course_basics",
        courseTitle = "Computer Basics",
        title = "Storage: HDD and SSD",
        readingTimeMinutes = 18,
        level = CourseLevel.BEGINNER,
        objectives = listOf(
          "Understand the definition and purpose of non-volatile secondary storage.",
          "Examine mechanical Hard Disk Drives (HDDs) and how magnetic spinning platters function.",
          "Explore Solid-State Drives (SSDs) and NAND flash memory architecture.",
          "Compare HDD vs SSD across speed, reliability, physical durability, capacity, and cost.",
          "Learn when to choose an SSD versus an HDD for modern storage needs."
        ),
        quickIntro = "While RAM handles your computer's short-term thoughts, your Storage Drive is its permanent long-term memory: preserving your operating system, software apps, family photos, games, and documents safely for years to come.",
        simpleExplanation = "Think of an HDD like an old-fashioned vinyl record player with a spinning record and a mechanical needle physically moving around. An SSD is like a microchip camera card: zero spinning parts, purely electrical, and up to 50 times faster!",
        detailedSections = listOf(
          "What is an HDD (Hard Disk Drive)?" to "Invented by IBM in the 1950s, the traditional HDD relies on mechanical moving parts:\n\n• Spinning Magnetic Platters: Aluminum or ceramic discs coated in microscopic magnetic grains, spinning at 5,400 or 7,200 Revolutions Per Minute (RPM).\n• Actuator Arm & Read/Write Heads: A mechanical arm hovers a microscopic fraction of a millimeter above the spinning disc, using magnetic pulses to flip microscopic magnetic domains (north/south = 1/0).\n• Limitation: Because the arm must physically move across the disc to find files (seek time), HDDs are slow (~100 to 150 MB/s) and vulnerable to physical shock if dropped.",
          "What is an SSD (Solid State Drive)?" to "SSDs represent the modern gold standard for computer storage:\n\n• Zero Moving Parts: Made entirely of silicon microchips called NAND Flash Memory.\n• Trapped Electron Cells: Data is stored by trapping electrons inside floating-gate transistors, retaining data even with zero electrical power for over a decade.\n• Form Factors: SATA 2.5-inch drives (~550 MB/s) and modern M.2 NVMe PCIe drives (reaching blistering speeds from 3,500 MB/s to over 7,400 MB/s!).\n• Instant Access: Near-zero seek time (0.05 milliseconds), making computers boot in 5 seconds instead of 60 seconds.",
          "Comprehensive Comparison Matrix" to "HDD vs SSD in Modern Computing:\n\n• Speed: SSD is 5x to 50x faster in reading and writing data.\n• Durability: SSD easily survives drops and vibrations; HDD can shatter or scratch if bumped while spinning.\n• Noise: SSD is 100% silent; HDD produces audible humming, clicking, and vibrational noise.\n• Power Consumption: SSD uses far less battery power, making laptops run cooler and longer.\n• Cost per Gigabyte: HDDs remain cheaper for mass storage (e.g. 10 Terabyte NAS backup servers); SSDs dominate all modern PCs and phones."
        ),
        realWorldExamples = listOf(
          RealWorldExample(
            title = "Booting Windows in 6 Seconds",
            description = "An NVMe SSD loads thousands of Windows OS system files into RAM in a blink, booting to the desktop in 6 seconds compared to 90 seconds on a legacy mechanical HDD.",
            iconName = "timer"
          ),
          RealWorldExample(
            title = "Dropping a Laptop",
            description = "If you drop a running laptop with an HDD, the spinning mechanical arm can crash into the disc platter, causing permanent data loss. An SSD has no moving parts and survives unscathed.",
            iconName = "shield"
          ),
          RealWorldExample(
            title = "Cloud Data Center Archiving",
            description = "Server companies like Google and Backblaze still use massive arrays of 20TB mechanical HDDs to store archival video backups and cold historical files economically.",
            iconName = "cloud"
          )
        ),
        howItWorksSteps = listOf(
          HowItWorksStep(1, "Operating System Request", "The OS asks the storage controller to read a specific file block (e.g., 'resume.docx')."),
          HowItWorksStep(2, "Controller Lookup", "The drive controller consults its internal allocation table to locate the exact sector or flash memory block."),
          HowItWorksStep(3, "Electrical / Magnetic Read", "HDD swings its actuator arm to the platter sector; SSD reads voltage threshold values directly from silicon cells."),
          HowItWorksStep(4, "Data Transfer", "Data bytes travel across SATA cables or high-speed PCIe lanes into system RAM for active use.")
        ),
        visualDiagramType = "STORAGE_HDD_SSD",
        importantPoints = listOf(
          "Storage is non-volatile; it holds all your operating system files, apps, and documents permanently.",
          "HDDs use physical spinning magnetic platters and mechanical read heads; SSDs use silent NAND flash memory chips.",
          "SSDs are up to 50 times faster than HDDs and have no fragile moving parts.",
          "Upgrading an older computer from an HDD to an SSD is the single biggest speed boost you can give a PC."
        ),
        commonMistakes = listOf(
          CommonMistake(
            mistake = "An SSD and RAM are basically the same thing because both use silicon memory chips.",
            correction = "RAM is volatile working memory that is erased on shutdown; SSD is permanent non-volatile storage that preserves your files forever."
          ),
          CommonMistake(
            mistake = "Mechanical hard drives need regular physical oiling or maintenance.",
            correction = "HDDs are hermetically sealed in factory cleanrooms. Opening an HDD outside a sterile environment ruins the disc instantly from microscopic dust particles."
          )
        ),
        practicalActivity = PracticalActivity(
          title = "Check What Drive Type You Have",
          objective = "Find out whether your computer uses an SSD or an HDD.",
          steps = listOf(
            "On Windows: Press Win + S, type 'Defragment and Optimize Drives', and open the app.",
            "Look under the 'Media type' column for your C: drive.",
            "Check whether it says 'Solid state drive' (SSD) or 'Hard disk drive' (HDD).",
            "Notice how Windows automatically recognizes SSDs to avoid unnecessary mechanical defragmentation!"
          )
        ),
        knowledgeCheck = LessonQuizQuestion(
          question = "What is the primary technical reason why SSDs are vastly faster and more shock-resistant than HDDs?",
          options = listOf(
            "SSDs use colder liquid coolant",
            "SSDs have zero mechanical moving parts and use silicon flash memory chips",
            "SSDs have bigger spinning discs inside",
            "SSDs only connect to Wi-Fi"
          ),
          correctOptionIndex = 1,
          explanation = "SSDs have no mechanical arms or spinning platters. They store data purely via electrical charges in silicon NAND flash chips, providing near-instant access and resilience against drops."
        ),
        prevLessonId = "cb_lesson_8",
        nextLessonId = "cb_lesson_10"
      ),

      // =================================================================
      // LESSON 10: Computer Ports and Connectors
      // =================================================================
      LessonDetailData(
        lessonId = "cb_lesson_10",
        lessonNumber = 10,
        totalLessons = 10,
        courseId = "course_basics",
        courseTitle = "Computer Basics",
        title = "Computer Ports and Connectors",
        readingTimeMinutes = 15,
        level = CourseLevel.BEGINNER,
        objectives = listOf(
          "Identify common computer ports and their unique physical shapes.",
          "Understand the evolution from legacy USB-A to universal modern USB-C.",
          "Differentiate video display standards: HDMI versus DisplayPort.",
          "Learn Ethernet networking, 3.5mm audio jacks, and power delivery standards.",
          "Practice safe connector handling to avoid pin damage and electrical static."
        ),
        quickIntro = "Computer ports are the physical external gateways that allow your machine to communicate with external peripherals, transmit high-definition video, connect to wired networks, and receive electrical power.",
        simpleExplanation = "Think of computer ports like different electrical outlets and doorways on a house. Just as you plug a garden hose into an outside spigot and a lamp into an electric wall outlet, computers have dedicated custom sockets shaped specifically for video, sound, data, and internet.",
        detailedSections = listOf(
          "USB: Universal Serial Bus Evolution" to "The most famous connection standard in personal computing:\n\n• USB-A: The classic rectangular connector found on almost every PC for decades. It is directional and only plugs in one way (the infamous 'three tries' connector).\n• USB-C: The modern oval, fully reversible universal connector. It can transmit high-speed data (up to 40 Gbps on Thunderbolt/USB4), carry high-resolution 8K video signals, and deliver up to 240 Watts of electrical power to charge laptops and phones through a single slender cable.",
          "High-Definition Video Ports" to "Connecting your computer to external monitors, TVs, and projectors:\n\n• HDMI (High-Definition Multimedia Interface): The universal consumer standard found on TVs, laptops, and game consoles. Transmits uncompressed digital video and multi-channel digital audio simultaneously.\n• DisplayPort (DP): The preferred choice of PC monitors and competitive gamers. Features higher bandwidth than HDMI, supporting ultra-high refresh rates (144Hz, 240Hz, 360Hz), FreeSync/G-Sync variable refresh rates, and daisy-chaining multiple monitors from one port.",
          "Networking, Audio & Power Ports" to "Essential specialized ports:\n\n• Ethernet (RJ-45): The 8-pin plastic modular connector with a locking clip that provides rock-solid, low-latency gigabit wired internet.\n• 3.5mm Audio Jack: The classic analog port with TRRS rings carrying stereo speaker output and microphone input.\n• Power Connectors: From dedicated DC barrel jacks on older laptops to magnetic MagSafe cables and modern USB-C Power Delivery (PD)."
        ),
        realWorldExamples = listOf(
          RealWorldExample(
            title = "Single-Cable Laptop Docking",
            description = "You sit at your desk and plug a single USB-C cable into your laptop. Instantly, your laptop charges at 100W, connects to two 4K external monitors, and connects to wired office Ethernet.",
            iconName = "dock"
          ),
          RealWorldExample(
            title = "Connecting a PC to Living Room TV",
            description = "Plugging an HDMI cable from your laptop into your TV immediately transmits pristine 4K video and stereo movie audio across the room with zero configuration.",
            iconName = "tv"
          ),
          RealWorldExample(
            title = "Competitive Gaming on Ethernet",
            description = "Switching from Wi-Fi to a wired Ethernet cable drops multiplayer game ping latency from 75ms down to 12ms and eliminates Wi-Fi signal dropouts.",
            iconName = "cable"
          )
        ),
        howItWorksSteps = listOf(
          HowItWorksStep(1, "Physical Insertion", "The male cable pins mate with the spring-loaded female receptacle contacts inside the computer port."),
          HowItWorksStep(2, "Hardware Handshake", "The port controller detects the voltage change and negotiates supported protocols (data speed, power wattage, display signals)."),
          HowItWorksStep(3, "Bus Addressing", "The operating system assigns a unique logical device address to the newly connected peripheral."),
          HowItWorksStep(4, "Active Communication", "Differential signaling transmits high-frequency binary data across twisted-pair copper traces.")
        ),
        visualDiagramType = "PORTS_CONNECTORS",
        importantPoints = listOf(
          "USB-C is reversible and can carry data, video, and electrical power simultaneously in a single cable.",
          "HDMI carries both digital video and audio to monitors and televisions.",
          "DisplayPort is the gold standard for high-refresh-rate PC gaming monitors.",
          "Never force a connector into a port; if it does not slide in smoothly, check its orientation and alignment."
        ),
        commonMistakes = listOf(
          CommonMistake(
            mistake = "Forcing a USB-A plug into a port upside down when it resists.",
            correction = "Never force a connector. USB-A only enters one way; inspect the plastic tab inside the plug before pushing."
          ),
          CommonMistake(
            mistake = "Assuming all USB-C cables are identical inside.",
            correction = "While the physical shape is identical, cheap USB-C cables may only support slow charging (USB 2.0 speed), while certified Thunderbolt cables support 40Gbps and dual 4K video."
          )
        ),
        practicalActivity = PracticalActivity(
          title = "Port Identification Challenge",
          objective = "Inspect and identify every port on your computer or laptop sides.",
          steps = listOf(
            "Look at the left, right, and back edges of your computer or laptop.",
            "Can you locate: A rectangular USB-A port? An oval USB-C port? An HDMI video port? A headphone audio jack?",
            "If using a smartphone, check its bottom edge to identify whether it uses modern USB-C or legacy micro-USB.",
            "Check for the Ethernet RJ45 port with its small spring-loaded latch!"
          )
        ),
        knowledgeCheck = LessonQuizQuestion(
          question = "Which modern reversible connector can simultaneously transmit 40Gbps data, 8K video signals, and up to 240W of charging power?",
          options = listOf(
            "USB-A",
            "VGA",
            "USB-C",
            "Ethernet RJ-45"
          ),
          correctOptionIndex = 2,
          explanation = "USB-C is the universal modern reversible connector that supports high-speed data, DisplayPort alternate mode for video, and USB Power Delivery (PD) up to 240W."
        ),
        prevLessonId = "cb_lesson_9",
        nextLessonId = null
      )
    )
  }
}
