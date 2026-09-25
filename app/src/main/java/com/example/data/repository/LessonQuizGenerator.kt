package com.example.data.repository

import com.example.data.model.Quiz
import com.example.data.model.QuizOption
import com.example.data.model.QuizQuestion
import com.example.data.model.QuizType

/**
 * Generates 5 multiple-choice questions based on individual lesson content,
 * allowing learners to test their understanding and earn mastery XP.
 */
object LessonQuizGenerator {

  fun generateQuizForLesson(
    lessonId: String,
    lessonTitle: String = "Lesson",
    courseId: String = "course_basics",
    courseTitle: String = "Computer Knowledge",
    lessonContent: String = ""
  ): Quiz {
    // 1. Check if there is already a curated 5-question quiz for this lesson
    val existingQuiz = ComputerBasicsQuizRepository.getQuizForLesson(lessonId)
    if (existingQuiz != null && existingQuiz.questions.size >= 5) {
      return existingQuiz
    }

    // 2. Generate 5 targeted multiple-choice questions based on lesson title and content
    val questions = generate5Questions(lessonId, lessonTitle, courseTitle, lessonContent)

    return Quiz(
      id = "quiz_$lessonId",
      title = "$lessonTitle Quiz",
      category = courseTitle,
      type = QuizType.LESSON,
      durationMinutes = 5,
      questions = questions,
      courseId = courseId,
      courseTitle = courseTitle,
      lessonId = lessonId,
      lessonTitle = lessonTitle
    )
  }

  private fun generate5Questions(
    lessonId: String,
    lessonTitle: String,
    courseTitle: String,
    content: String
  ): List<QuizQuestion> {
    val lowerTitle = lessonTitle.lowercase()
    val lowerCourse = courseTitle.lowercase()

    return when {
      // -------------------------------------------------------------
      // EXCEL / SPREADSHEETS
      // -------------------------------------------------------------
      lowerTitle.contains("excel") || lowerTitle.contains("formula") || lowerTitle.contains("cell") || lowerCourse.contains("excel") -> {
        listOf(
          QuizQuestion(
            id = "${lessonId}_q1",
            question = "In spreadsheet software like Microsoft Excel, which symbol must precede every calculation or formula?",
            options = listOf(
              QuizOption(0, "The colon symbol (:)"),
              QuizOption(1, "The equals sign (=)"),
              QuizOption(2, "The hash symbol (#)"),
              QuizOption(3, "The ampersand (&)")
            ),
            correctOptionIndex = 1,
            explanation = "Every formula in Excel must begin with an equals sign (=) so Excel knows to calculate the expression rather than treat it as plain text."
          ),
          QuizQuestion(
            id = "${lessonId}_q2",
            question = "What is the difference between an absolute cell reference (\$A\$1) and a relative cell reference (A1)?",
            options = listOf(
              QuizOption(0, "Absolute references never change when copied across rows or columns"),
              QuizOption(1, "Relative references can only reference text cells"),
              QuizOption(2, "Absolute references double the computed numerical value"),
              QuizOption(3, "Relative references are permanently locked to cell A1")
            ),
            correctOptionIndex = 0,
            explanation = "The dollar sign ($) in an absolute reference locks the row and column coordinates, keeping them constant when the formula is filled or copied."
          ),
          QuizQuestion(
            id = "${lessonId}_q3",
            question = "Which formula calculates the arithmetic average of numeric values residing in cells B2 through B10?",
            options = listOf(
              QuizOption(0, "=MEAN(B2:B10)"),
              QuizOption(1, "=SUM(B2:B10) / COUNTALL"),
              QuizOption(2, "=AVERAGE(B2:B10)"),
              QuizOption(3, "=MEDIAN.AVG(B2:B10)")
            ),
            correctOptionIndex = 2,
            explanation = "=AVERAGE(range) calculates the mathematical mean of all numerical values in the designated cell range."
          ),
          QuizQuestion(
            id = "${lessonId}_q4",
            question = "What primary purpose does a PivotTable serve in data analysis?",
            options = listOf(
              QuizOption(0, "It reformats sheet fonts and borders automatically"),
              QuizOption(1, "It rapidly summarizes, reorganizes, and aggregates large datasets without modifying the source data"),
              QuizOption(2, "It encrypts the spreadsheet file with a cryptographic password"),
              QuizOption(3, "It converts numeric tables into raw plain-text CSV files")
            ),
            correctOptionIndex = 1,
            explanation = "PivotTables allow users to slice, filter, group, and aggregate multidimensional data to reveal trends and summaries instantly."
          ),
          QuizQuestion(
            id = "${lessonId}_q5",
            question = "What does the error value #DIV/0! indicate when displayed in a worksheet cell?",
            options = listOf(
              QuizOption(0, "The column is too narrow to display the number"),
              QuizOption(1, "The formula contains an invalid function name"),
              QuizOption(2, "A formula attempted to divide a number by zero or an empty cell"),
              QuizOption(3, "The worksheet reached its maximum allowed row capacity")
            ),
            correctOptionIndex = 2,
            explanation = "#DIV/0! is thrown whenever a formula attempts a mathematically undefined division by zero or refers to an empty denominator cell."
          )
        )
      }

      // -------------------------------------------------------------
      // WORD / DOCUMENT PROCESSING
      // -------------------------------------------------------------
      lowerTitle.contains("word") || lowerTitle.contains("document") || lowerTitle.contains("paragraph") || lowerCourse.contains("word") -> {
        listOf(
          QuizQuestion(
            id = "${lessonId}_q1",
            question = "What is the primary advantage of utilizing predefined Styles (Heading 1, Heading 2) instead of manual bolding in documents?",
            options = listOf(
              QuizOption(0, "Styles reduce the exported file size by 90%"),
              QuizOption(1, "Styles maintain consistent typography and enable automatic Table of Contents generation"),
              QuizOption(2, "Styles prevent unauthorized users from viewing the text"),
              QuizOption(3, "Styles force all pages to print in landscape orientation")
            ),
            correctOptionIndex = 1,
            explanation = "Styles establish semantic hierarchy, allowing Word to construct navigable headings, outlines, and automated Tables of Contents."
          ),
          QuizQuestion(
            id = "${lessonId}_q2",
            question = "What keyboard shortcut quickly copies selected formatting and applies it to other text via the Format Painter?",
            options = listOf(
              QuizOption(0, "Ctrl + C followed by Ctrl + V"),
              QuizOption(1, "Ctrl + Shift + C to copy format, Ctrl + Shift + V to paste format"),
              QuizOption(2, "Alt + F4"),
              QuizOption(3, "Ctrl + P")
            ),
            correctOptionIndex = 1,
            explanation = "Ctrl + Shift + C copies only typography/formatting attributes without copying text, and Ctrl + Shift + V pastes those attributes onto new text."
          ),
          QuizQuestion(
            id = "${lessonId}_q3",
            question = "Which feature should be used to send individualized letters or certificates to hundreds of recipients using a single template?",
            options = listOf(
              QuizOption(0, "Track Changes"),
              QuizOption(1, "Mail Merge"),
              QuizOption(2, "AutoCorrect"),
              QuizOption(3, "Macro Recorder")
            ),
            correctOptionIndex = 1,
            explanation = "Mail Merge connects a Word document template to an external data source (like Excel or Access) to generate personalized outputs in bulk."
          ),
          QuizQuestion(
            id = "${lessonId}_q4",
            question = "Why should you insert a 'Section Break (Next Page)' rather than a standard 'Page Break'?",
            options = listOf(
              QuizOption(0, "Section Breaks allow differing headers, footers, margins, or orientations on different pages"),
              QuizOption(1, "Section Breaks save printer toner during physical output"),
              QuizOption(2, "Page Breaks can only be inserted at the end of a document"),
              QuizOption(3, "Section Breaks convert the document into a PDF file")
            ),
            correctOptionIndex = 0,
            explanation = "Section breaks divide documents into independent layout sections, allowing unique page numbering, orientations (landscape vs portrait), and headers."
          ),
          QuizQuestion(
            id = "${lessonId}_q5",
            question = "What is the main benefit of turning on 'Track Changes' during collaborative editing?",
            options = listOf(
              QuizOption(0, "It locks the document preventing anyone from typing"),
              QuizOption(1, "It records insertions, deletions, and comments visually for review and approval"),
              QuizOption(2, "It automatically emails the document to the system administrator"),
              QuizOption(3, "It highlights spelling errors in red underline only")
            ),
            correctOptionIndex = 1,
            explanation = "Track Changes marks all additions and strikethroughs, enabling authors to systematically accept or reject each editorial modification."
          )
        )
      }

      // -------------------------------------------------------------
      // PROGRAMMING & CODING
      // -------------------------------------------------------------
      lowerTitle.contains("program") || lowerTitle.contains("code") || lowerTitle.contains("logic") || lowerTitle.contains("variable") || lowerCourse.contains("programming") -> {
        listOf(
          QuizQuestion(
            id = "${lessonId}_q1",
            question = "In computer programming, what is a variable best described as?",
            options = listOf(
              QuizOption(0, "A permanent physical component soldered onto the CPU"),
              QuizOption(1, "A named memory location that stores a value which can change during program execution"),
              QuizOption(2, "An unrecoverable syntax error in compiler output"),
              QuizOption(3, "A specialized printer protocol")
            ),
            correctOptionIndex = 1,
            explanation = "A variable is an identified memory container holding data (such as integers, text, or booleans) that code can read and modify dynamically."
          ),
          QuizQuestion(
            id = "${lessonId}_q2",
            question = "Which control flow structure repeatedly executes a block of code while a specific logical condition evaluates to true?",
            options = listOf(
              QuizOption(0, "If-Else conditional"),
              QuizOption(1, "While or For Loop"),
              QuizOption(2, "Class definition"),
              QuizOption(3, "Return statement")
            ),
            correctOptionIndex = 1,
            explanation = "Loops (such as while loops and for loops) iterate repeatedly over code instructions until their termination condition is reached."
          ),
          QuizQuestion(
            id = "${lessonId}_q3",
            question = "What data type would be most appropriate for storing whether a user's account is currently active or deactivated?",
            options = listOf(
              QuizOption(0, "Floating-point (Float)"),
              QuizOption(1, "Boolean (true / false)"),
              QuizOption(2, "Character array (Char[])"),
              QuizOption(3, "Double-precision 64-bit integer")
            ),
            correctOptionIndex = 1,
            explanation = "Booleans represent binary true/false values, perfectly suited for binary state flags like active/inactive."
          ),
          QuizQuestion(
            id = "${lessonId}_q4",
            question = "What is an algorithm in computer science?",
            options = listOf(
              QuizOption(0, "A computer screen calibration profile"),
              QuizOption(1, "A finite, unambiguous sequence of step-by-step instructions to solve a problem"),
              QuizOption(2, "The hardware serial number of a graphics card"),
              QuizOption(3, "A malicious trojan horse program")
            ),
            correctOptionIndex = 1,
            explanation = "An algorithm is a well-defined procedural sequence of steps designed to transform inputs into the desired output or solve a specific computational problem."
          ),
          QuizQuestion(
            id = "${lessonId}_q5",
            question = "What fundamental programming concept promotes code reuse and modularity by packaging logic into callable blocks?",
            options = listOf(
              QuizOption(0, "Functions (or Methods)"),
              QuizOption(1, "Hardcoded magic numbers"),
              QuizOption(2, "Infinite recursive calls"),
              QuizOption(3, "Single monolithic files")
            ),
            correctOptionIndex = 0,
            explanation = "Functions allow developers to write logic once, accept arguments, return values, and invoke that logic from multiple places cleanly."
          )
        )
      }

      // -------------------------------------------------------------
      // DATABASES & SQL
      // -------------------------------------------------------------
      lowerTitle.contains("database") || lowerTitle.contains("sql") || lowerCourse.contains("database") -> {
        listOf(
          QuizQuestion(
            id = "${lessonId}_q1",
            question = "In a relational database table, what is the role of a Primary Key?",
            options = listOf(
              QuizOption(0, "It stores the user's secret password in encrypted format"),
              QuizOption(1, "It uniquely identifies each distinct record or row in the table"),
              QuizOption(2, "It allows unlimited duplicate rows in the database"),
              QuizOption(3, "It connects the database directly to the internet without security")
            ),
            correctOptionIndex = 1,
            explanation = "A Primary Key is a column (or set of columns) guaranteed to be unique and non-null for every row in a relational database table."
          ),
          QuizQuestion(
            id = "${lessonId}_q2",
            question = "Which SQL clause is used to filter records so that only rows meeting specific criteria are retrieved?",
            options = listOf(
              QuizOption(0, "ORDER BY"),
              QuizOption(1, "WHERE"),
              QuizOption(2, "GROUP BY"),
              QuizOption(3, "LIMIT")
            ),
            correctOptionIndex = 1,
            explanation = "The WHERE clause filters query results based on logical conditions (e.g., WHERE age >= 18)."
          ),
          QuizQuestion(
            id = "${lessonId}_q3",
            question = "What does the SQL command 'SELECT * FROM Students;' accomplish?",
            options = listOf(
              QuizOption(0, "Deletes all records from the Students table"),
              QuizOption(1, "Retrieves all columns and rows from the Students table"),
              QuizOption(2, "Multiplies all numerical values in the table by asterisk"),
              QuizOption(3, "Creates a new table named Students")
            ),
            correctOptionIndex = 1,
            explanation = "The asterisk (*) acts as a wildcard representing all columns, fetching every column and record currently in the Students table."
          ),
          QuizQuestion(
            id = "${lessonId}_q4",
            question = "Which type of SQL JOIN returns all rows from the left table, and matching rows from the right table?",
            options = listOf(
              QuizOption(0, "INNER JOIN"),
              QuizOption(1, "LEFT JOIN (LEFT OUTER JOIN)"),
              QuizOption(2, "CROSS JOIN"),
              QuizOption(3, "FULL JOIN without conditions")
            ),
            correctOptionIndex = 1,
            explanation = "A LEFT JOIN preserves every row from the left table; where no match exists on the right table, NULL values are filled in."
          ),
          QuizQuestion(
            id = "${lessonId}_q5",
            question = "What is a Foreign Key in relational database architecture?",
            options = listOf(
              QuizOption(0, "A key imported from a foreign country's server"),
              QuizOption(1, "A column in one table that links to the primary key of another table"),
              QuizOption(2, "A temporary password sent via SMS"),
              QuizOption(3, "A backup copy of the database index")
            ),
            correctOptionIndex = 1,
            explanation = "Foreign Keys enforce referential integrity by establishing cross-table relationships between dependent and parent tables."
          )
        )
      }

      // -------------------------------------------------------------
      // CYBERSECURITY & SAFETY
      // -------------------------------------------------------------
      lowerTitle.contains("security") || lowerTitle.contains("cyber") || lowerTitle.contains("protect") || lowerCourse.contains("security") -> {
        listOf(
          QuizQuestion(
            id = "${lessonId}_q1",
            question = "What is Multi-Factor Authentication (MFA/2FA)?",
            options = listOf(
              QuizOption(0, "Using the same password across multiple online accounts"),
              QuizOption(1, "Requiring two or more independent credentials (e.g. password + phone code) to verify identity"),
              QuizOption(2, "Encrypting the hard drive twice with separate keys"),
              QuizOption(3, "Having two people type the password simultaneously")
            ),
            correctOptionIndex = 1,
            explanation = "MFA combines something you know (password) with something you have (authenticator app / hardware token) or are (biometrics)."
          ),
          QuizQuestion(
            id = "${lessonId}_q2",
            question = "Which social engineering attack tricks victims into revealing sensitive credentials by masquerading as a trustworthy entity via fraudulent emails?",
            options = listOf(
              QuizOption(0, "DDoS attack"),
              QuizOption(1, "Phishing"),
              QuizOption(2, "Buffer overflow"),
              QuizOption(3, "Man-in-the-middle")
            ),
            correctOptionIndex = 1,
            explanation = "Phishing relies on deceptive emails or fake login portals designed to manipulate targets into surrendering passwords or banking information."
          ),
          QuizQuestion(
            id = "${lessonId}_q3",
            question = "What does end-to-end encryption (E2EE) ensure during digital communications?",
            options = listOf(
              QuizOption(0, "Internet service providers can inspect every message for viruses"),
              QuizOption(1, "Only the communicating endpoints (sender and recipient) can decrypt and read the messages"),
              QuizOption(2, "Messages are broadcasted publicly across all nodes on the network"),
              QuizOption(3, "The device battery charges faster while sending texts")
            ),
            correctOptionIndex = 1,
            explanation = "E2EE ensures that intermediate servers, ISPs, and eavesdroppers cannot decrypt or view data transiting between users."
          ),
          QuizQuestion(
            id = "${lessonId}_q4",
            question = "Why is applying operating system and software security patches critical?",
            options = listOf(
              QuizOption(0, "Patches remediate known security vulnerabilities before attackers can exploit them"),
              QuizOption(1, "Patches change your desktop wallpaper to notify you of safety"),
              QuizOption(2, "Patches prevent physical dust from accumulating inside the PC"),
              QuizOption(3, "Patches delete all stored user documents automatically")
            ),
            correctOptionIndex = 0,
            explanation = "Software vendors release security patches to seal discovered exploits and zero-day vulnerabilities targeted by cyber threats."
          ),
          QuizQuestion(
            id = "${lessonId}_q5",
            question = "What is ransomware?",
            options = listOf(
              QuizOption(0, "Software that speeds up your internet connection for a fee"),
              QuizOption(1, "Malware that encrypts victim files and demands payment to restore access"),
              QuizOption(2, "Hardware that monitors router bandwidth"),
              QuizOption(3, "An open-source text editing utility")
            ),
            correctOptionIndex = 1,
            explanation = "Ransomware locks victim data using strong encryption and extorts financial ransom in exchange for a decryption key."
          )
        )
      }

      // -------------------------------------------------------------
      // NETWORKING & INTERNET
      // -------------------------------------------------------------
      lowerTitle.contains("network") || lowerTitle.contains("internet") || lowerCourse.contains("network") -> {
        listOf(
          QuizQuestion(
            id = "${lessonId}_q1",
            question = "What critical service does the Domain Name System (DNS) perform on the internet?",
            options = listOf(
              QuizOption(0, "It translates human-friendly domain names (e.g. google.com) into numerical IP addresses"),
              QuizOption(1, "It compresses video streams for faster loading"),
              QuizOption(2, "It assigns hardware serial numbers to network cards"),
              QuizOption(3, "It manages computer screen refresh rates")
            ),
            correctOptionIndex = 0,
            explanation = "DNS acts as the phonebook of the internet, mapping readable domain names to machine-routable IP addresses."
          ),
          QuizQuestion(
            id = "${lessonId}_q2",
            question = "Which network device connects multiple local devices together and directs data packets between different networks (e.g. home LAN to the Internet)?",
            options = listOf(
              QuizOption(0, "Power supply unit"),
              QuizOption(1, "Router"),
              QuizOption(2, "RAM module"),
              QuizOption(3, "Sound card")
            ),
            correctOptionIndex = 1,
            explanation = "Routers examine network packet headers and route traffic between distinct networks using IP routing protocols."
          ),
          QuizQuestion(
            id = "${lessonId}_q3",
            question = "What distinguishes an IPv4 address from an IPv6 address?",
            options = listOf(
              QuizOption(0, "IPv4 uses 32 bits (4 numbers), while IPv6 uses 128 bits providing vastly more addresses"),
              QuizOption(1, "IPv4 is only used for smartphones, while IPv6 is for desktop computers"),
              QuizOption(2, "IPv6 cannot transmit web traffic"),
              QuizOption(3, "IPv4 is wireless only, while IPv6 requires physical fiber optic cables")
            ),
            correctOptionIndex = 0,
            explanation = "IPv4 addresses are 32-bit (~4.3 billion addresses), whereas IPv6 provides 128 bits (340 undecillion addresses) to eliminate IP exhaustion."
          ),
          QuizQuestion(
            id = "${lessonId}_q4",
            question = "What does the 'S' in HTTPS signify when browsing websites?",
            options = listOf(
              QuizOption(0, "Speed (accelerated loading protocol)"),
              QuizOption(1, "Secure (encrypted via TLS/SSL)"),
              QuizOption(2, "Server (direct connection to datacenter)"),
              QuizOption(3, "Standard (plain-text default protocol)")
            ),
            correctOptionIndex = 1,
            explanation = "HTTPS encrypts the HTTP communication channel using TLS/SSL cryptographic protocols, protecting credentials and session data."
          ),
          QuizQuestion(
            id = "${lessonId}_q5",
            question = "What command-line diagnostic tool is used to test connectivity and packet latency between your device and a remote server?",
            options = listOf(
              QuizOption(0, "copy"),
              QuizOption(1, "ping"),
              QuizOption(2, "format"),
              QuizOption(3, "mkdir")
            ),
            correctOptionIndex = 1,
            explanation = "The 'ping' utility sends ICMP echo request packets to a destination host and measures the round-trip latency and packet loss."
          )
        )
      }

      // -------------------------------------------------------------
      // HARDWARE & ARCHITECTURE
      // -------------------------------------------------------------
      lowerTitle.contains("hardware") || lowerTitle.contains("cpu") || lowerTitle.contains("ram") || lowerTitle.contains("storage") || lowerCourse.contains("hardware") -> {
        listOf(
          QuizQuestion(
            id = "${lessonId}_q1",
            question = "What is the primary role of the Central Processing Unit (CPU)?",
            options = listOf(
              QuizOption(0, "To store permanent files like videos and photos when power is turned off"),
              QuizOption(1, "To fetch, decode, and execute program instructions and coordinate computer operations"),
              QuizOption(2, "To convert alternating current into direct current"),
              QuizOption(3, "To display colors onto the external monitor")
            ),
            correctOptionIndex = 1,
            explanation = "The CPU is the central brain of the computer that carries out arithmetic, logic, and control instructions of running programs."
          ),
          QuizQuestion(
            id = "${lessonId}_q2",
            question = "Why is RAM (Random Access Memory) characterized as 'volatile' memory?",
            options = listOf(
              QuizOption(0, "It can physically explode if overheated"),
              QuizOption(1, "It requires constant electrical power to retain data; everything is lost when powered off"),
              QuizOption(2, "It cannot read data in sequential order"),
              QuizOption(3, "It permanently burns bits onto silicone wafers")
            ),
            correctOptionIndex = 1,
            explanation = "Volatile memory requires active electric current; once the device is shut down, all data in RAM evaporates immediately."
          ),
          QuizQuestion(
            id = "${lessonId}_q3",
            question = "Which storage technology provides substantially faster read/write speeds and zero moving mechanical parts?",
            options = listOf(
              QuizOption(0, "Traditional Magnetic Hard Disk Drive (HDD)"),
              QuizOption(1, "Solid State Drive (SSD / NVMe)"),
              QuizOption(2, "Optical Compact Disc (CD-ROM)"),
              QuizOption(3, "Floppy Diskette")
            ),
            correctOptionIndex = 1,
            explanation = "SSDs utilize NAND flash memory chips, eliminating spinning magnetic platters and mechanical read heads for rapid access."
          ),
          QuizQuestion(
            id = "${lessonId}_q4",
            question = "What main circuit board connects the CPU, memory, expansion cards, and peripherals together?",
            options = listOf(
              QuizOption(0, "Motherboard"),
              QuizOption(1, "Heatsink"),
              QuizOption(2, "Power Supply (PSU)"),
              QuizOption(3, "Network Interface Card")
            ),
            correctOptionIndex = 0,
            explanation = "The motherboard is the master printed circuit board (PCB) that houses electronic traces, buses, sockets, and chipset controllers."
          ),
          QuizQuestion(
            id = "${lessonId}_q5",
            question = "What is the primary purpose of thermal paste applied between the CPU heat spreader and cooler heatsink?",
            options = listOf(
              QuizOption(0, "To permanently glue the CPU into the motherboard socket"),
              QuizOption(1, "To eliminate microscopic air pockets and maximize conductive heat transfer"),
              QuizOption(2, "To conduct electrical current directly into the CPU cores"),
              QuizOption(3, "To provide pleasant decorative aesthetics")
            ),
            correctOptionIndex = 1,
            explanation = "Air is a poor heat conductor. Thermal paste fills microscopic surface imperfections, facilitating efficient heat transfer to the cooler."
          )
        )
      }

      // -------------------------------------------------------------
      // DEFAULT / GENERAL COMPUTER LESSON (5 Targeted Questions)
      // -------------------------------------------------------------
      else -> {
        listOf(
          QuizQuestion(
            id = "${lessonId}_q1",
            question = "What is the primary objective and core concept explored in '$lessonTitle'?",
            options = listOf(
              QuizOption(0, "Applying foundational principles to solve practical computing challenges"),
              QuizOption(1, "Bypassing hardware safety limits to increase fan speed"),
              QuizOption(2, "Replacing all digital computers with manual paper records"),
              QuizOption(3, "Disconnecting electrical power while running complex tasks")
            ),
            correctOptionIndex = 0,
            explanation = "Mastering '$lessonTitle' equips learners with essential knowledge and practical skills in $courseTitle."
          ),
          QuizQuestion(
            id = "${lessonId}_q2",
            question = "Which best practice should always be followed when working with topics covered in '$lessonTitle'?",
            options = listOf(
              QuizOption(0, "Ignoring system error prompts and alerts"),
              QuizOption(1, "Understanding the underlying workflow, verifying inputs, and following structured steps"),
              QuizOption(2, "Saving files only once per year without backups"),
              QuizOption(3, "Sharing system administrative credentials publicly")
            ),
            correctOptionIndex = 1,
            explanation = "Structured methodologies and input verification prevent errors and reinforce reliable computing results."
          ),
          QuizQuestion(
            id = "${lessonId}_q3",
            question = "How does understanding '$lessonTitle' directly benefit real-world computer users?",
            options = listOf(
              QuizOption(0, "It forces older devices to stop functioning"),
              QuizOption(1, "It improves productivity, troubleshooting speed, and technical confidence"),
              QuizOption(2, "It consumes excess battery life unnecessarily"),
              QuizOption(3, "It eliminates the need for software updates forever")
            ),
            correctOptionIndex = 1,
            explanation = "Practical technical comprehension reduces frustration, speeds up daily tasks, and builds independent problem-solving competence."
          ),
          QuizQuestion(
            id = "${lessonId}_q4",
            question = "What common pitfall should learners actively avoid when practicing '$lessonTitle'?",
            options = listOf(
              QuizOption(0, "Rushing through steps without reviewing feedback or error indicators"),
              QuizOption(1, "Practicing hands-on exercises carefully"),
              QuizOption(2, "Taking organized study notes"),
              QuizOption(3, "Reviewing key lesson summaries")
            ),
            correctOptionIndex = 0,
            explanation = "Skipping verification or ignoring feedback messages often leads to confusion and preventable misconfigurations."
          ),
          QuizQuestion(
            id = "${lessonId}_q5",
            question = "When completing tasks related to '$lessonTitle', what is the recommended next step to ensure retention?",
            options = listOf(
              QuizOption(0, "Immediately turn off the computer and never practice again"),
              QuizOption(1, "Complete the knowledge quiz, test the concept in a practical activity, and review study notes"),
              QuizOption(2, "Delete all installed applications"),
              QuizOption(3, "Reset the device to factory defaults")
            ),
            correctOptionIndex = 1,
            explanation = "Active recall through quizzes and hands-on activities transfers theoretical understanding into durable long-term memory."
          )
        )
      }
    }
  }
}
