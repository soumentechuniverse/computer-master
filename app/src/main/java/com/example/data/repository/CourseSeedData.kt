package com.example.data.repository

import com.example.data.model.Chapter
import com.example.data.model.Course
import com.example.data.model.CourseLevel
import com.example.data.model.CourseModule
import com.example.data.model.Lesson

object CourseSeedData {

  fun getInitialCourses(): List<Course> {
    val definedCourses = listOf(
      // =================================================================
      // 1. Computer Fundamentals
      // =================================================================
      Course(
        id = "course_basics",
        title = "Computer Basics",
        description = "Understand computer hardware, CPU, RAM, storage devices, motherboards, input/output peripherals, and how computing works.",
        level = CourseLevel.BEGINNER,
        difficulty = 1,
        lessonCount = 10,
        estimatedHours = 2.6,
        iconName = "computer",
        progressPercent = 30,
        isBookmarked = true,
        tags = listOf("Fundamentals", "Hardware", "CPU", "RAM", "Components"),
        modules = listOf(
          CourseModule(
            id = "mod_b1",
            title = "Foundations of Computing",
            chapters = listOf(
              Chapter(
                id = "chap_b1_1",
                title = "Introduction to Computers",
                lessons = listOf(
                  Lesson("cb_lesson_1", "What is a Computer?", 12, "Definition, input-processing-output-storage cycle, and everyday devices.", true),
                  Lesson("cb_lesson_2", "How Does a Computer Work?", 15, "Step-by-step journey of data through CPU, memory, and storage.", true),
                  Lesson("cb_lesson_3", "Hardware and Software", 15, "Physical parts vs digital instructions, system software vs applications.", true)
                )
              ),
              Chapter(
                id = "chap_b1_2",
                title = "Interacting with Computers",
                lessons = listOf(
                  Lesson("cb_lesson_4", "Input Devices", 14, "Keyboards, mice, touchscreens, mics, webcams, and scanners.", false),
                  Lesson("cb_lesson_5", "Output Devices", 14, "Monitors, printers, speakers, headphones, and projectors.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_b2",
            title = "Internal Architecture & Connectivity",
            chapters = listOf(
              Chapter(
                id = "chap_b2_1",
                title = "The Engine & Memory",
                lessons = listOf(
                  Lesson("cb_lesson_6", "CPU", 18, "Central Processing Unit, ALU, Control Unit, registers, and clock speed.", false),
                  Lesson("cb_lesson_7", "RAM", 16, "Random Access Memory, temporary volatile workspace, and multitasking.", false),
                  Lesson("cb_lesson_8", "ROM", 14, "Read-Only Memory, non-volatile BIOS/UEFI firmware, and system boot.", false)
                )
              ),
              Chapter(
                id = "chap_b2_2",
                title = "Storage & Connectivity",
                lessons = listOf(
                  Lesson("cb_lesson_9", "Storage: HDD and SSD", 18, "Magnetic platters vs flash NAND chips, speed, durability, and capacities.", false),
                  Lesson("cb_lesson_10", "Computer Ports and Connectors", 15, "USB-A, USB-C, HDMI, DisplayPort, Ethernet, and port safety.", false)
                )
              )
            )
          )
        )
      ),

      // =================================================================
      // 2. Computer Hardware
      // =================================================================
      Course(
        id = "course_hardware",
        title = "Keyboard & Typing",
        description = "Learn keyboard key groups, correct finger placement, touch typing, essential shortcuts, and ways to improve typing speed and accuracy.",
        level = CourseLevel.BEGINNER,
        difficulty = 1,
        lessonCount = 0,
        estimatedHours = 0.0,
        iconName = "keyboard",
        progressPercent = 0,
        isBookmarked = false,
        tags = listOf("Keyboard", "Typing", "Shortcuts", "Accuracy"),
        modules = emptyList()
      ),

      // =================================================================
      // 3. Software
      // =================================================================
      Course(
        id = "course_software",
        title = "C/C++ Programming",
        description = "Learn C and C++ programming through variables, conditions, loops, functions, arrays, pointers, debugging, and small console projects.",
        level = CourseLevel.BEGINNER,
        difficulty = 1,
        lessonCount = 0,
        estimatedHours = 0.0,
        iconName = "code",
        progressPercent = 0,
        isBookmarked = false,
        tags = listOf("C", "C++", "Programming", "Compiler", "Debugging"),
        modules = emptyList()
      ),

      // =================================================================
      // 4. Operating Systems
      // =================================================================
      Course(
        id = "course_os",
        title = "Linux Fundamentals",
        description = "Learn Linux distributions, terminal commands, file paths, permissions, packages, processes, networking, and safe shell scripting.",
        level = CourseLevel.BEGINNER,
        difficulty = 1,
        lessonCount = 0,
        estimatedHours = 0.0,
        iconName = "terminal",
        progressPercent = 0,
        isBookmarked = false,
        tags = listOf("Linux", "Terminal", "Commands", "Permissions", "Shell"),
        modules = emptyList()
      ),

      // =================================================================
      // 5. Windows
      // =================================================================
      Course(
        id = "course_windows",
        title = "Windows & File Management",
        description = "Master Windows navigation, desktop personalization, Task Manager, system settings, and keyboard shortcuts for maximum speed.",
        level = CourseLevel.BEGINNER,
        difficulty = 1,
        lessonCount = 7,
        estimatedHours = 2.5,
        iconName = "windows",
        progressPercent = 28,
        tags = listOf("Windows", "OS", "Files", "Productivity"),
        modules = listOf(
          CourseModule(
            id = "mod_w1",
            title = "OS Navigation & Organization",
            chapters = listOf(
              Chapter(
                id = "chap_w1_1",
                title = "Windows 11 Interface",
                lessons = listOf(
                  Lesson("les_w1", "Desktop, Taskbar & Modern Settings App", 15, "Personalization, system updates, and notification controls.", true),
                  Lesson("les_w2", "File Explorer: Folders, Paths & Ribbon", 20, "Navigation pane, file paths, hidden files, and attributes.", true)
                )
              ),
              Chapter(
                id = "chap_w1_2",
                title = "File Structure & Formats",
                lessons = listOf(
                  Lesson("les_w3", "File Extensions, Attributes & Default Programs", 15, "Understanding .exe, .pdf, .zip, and default application mappings.", false),
                  Lesson("les_w4", "Organizing & Archiving with Zip Files", 15, "Compression ratios, extraction, and directory best practices.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_w2",
            title = "System Management & Tools",
            chapters = listOf(
              Chapter(
                id = "chap_w2_1",
                title = "Process Monitoring & Cloud",
                lessons = listOf(
                  Lesson("les_w5", "Task Manager: Ending Tasks & Startup Programs", 18, "Killing frozen tasks, startup apps, and CPU/RAM graphs.", false),
                  Lesson("les_w6", "Windows Backup, Cloud Sync & Restoring Files", 16, "OneDrive sync, restoring previous file versions, and external drives.", false)
                )
              ),
              Chapter(
                id = "chap_w2_2",
                title = "Speed & Shortcuts",
                lessons = listOf(
                  Lesson("les_w7", "Power User Windows Keyboard Shortcuts", 15, "Win+V clipboard history, snapping windows, virtual desktops.", false)
                )
              )
            )
          )
        )
      ),

      // =================================================================
      // 6. Files and Folders
      // =================================================================
      Course(
        id = "course_files",
        title = "App Development",
        description = "Plan, design, build, test, and publish mobile apps; learn screens, UI, navigation, local data, APIs, accessibility, and versioning.",
        level = CourseLevel.BEGINNER,
        difficulty = 1,
        lessonCount = 0,
        estimatedHours = 0.0,
        iconName = "phone_android",
        progressPercent = 0,
        isBookmarked = false,
        tags = listOf("App Development", "Android", "UI", "Testing", "Publishing"),
        modules = emptyList()
      ),

      // =================================================================
      // 7. Internet
      // =================================================================
      Course(
        id = "course_internet",
        title = "Internet & Email",
        description = "Navigate the web safely, use advanced search operators, master email etiquette, avoid phishing scams, and organize cloud storage.",
        level = CourseLevel.BEGINNER,
        difficulty = 1,
        lessonCount = 6,
        estimatedHours = 2.0,
        iconName = "globe",
        progressPercent = 80,
        tags = listOf("Internet", "Email", "Web", "Cloud"),
        modules = listOf(
          CourseModule(
            id = "mod_ie1",
            title = "Web Navigation & Safety",
            chapters = listOf(
              Chapter(
                id = "chap_ie1_1",
                title = "Browsers & Search",
                lessons = listOf(
                  Lesson("les_ie1", "How the Web Works: URLs, DNS & Browsers", 15, "Demystifying client-server architecture and domain names.", true),
                  Lesson("les_ie2", "Advanced Google Search Operators & Filters", 18, "Using site:, filetype:, quotes, and minus operators.", true)
                )
              ),
              Chapter(
                id = "chap_ie1_2",
                title = "Cyber Hygiene Basics",
                lessons = listOf(
                  Lesson("les_ie3", "HTTPS, Cookies, Cache & Private Browsing", 16, "Understanding site encryption and digital footprint tracking.", true),
                  Lesson("les_ie4", "Spotting Phishing Scams, Spoof URLs & Malware", 20, "Sender verification, suspicious attachments, and fraud prevention.", true)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_ie2",
            title = "Communication & Cloud Storage",
            chapters = listOf(
              Chapter(
                id = "chap_ie2_1",
                title = "Professional Workflows",
                lessons = listOf(
                  Lesson("les_ie5", "Professional Email Formatting (CC, BCC & Signatures)", 18, "Formal etiquette, subject lines, and attachment protocols.", false),
                  Lesson("les_ie6", "Cloud Storage: Google Drive, OneDrive & File Sharing", 15, "Permissions, sharing links, and folder organization.", false)
                )
              )
            )
          )
        )
      ),

      // =================================================================
      // 8. Networking
      // =================================================================
      Course(
        id = "course_networking",
        title = "Networking",
        description = "Master the OSI model, TCP/IP, IP addressing, subnetting, DNS, DHCP, routing, switches, firewalls, and network troubleshooting tools.",
        level = CourseLevel.INTERMEDIATE,
        difficulty = 2,
        lessonCount = 8,
        estimatedHours = 4.5,
        iconName = "network",
        progressPercent = 40,
        tags = listOf("Networking", "TCP/IP", "DNS", "Subnetting"),
        modules = listOf(
          CourseModule(
            id = "mod_net1",
            title = "Network Models & Addressing",
            chapters = listOf(
              Chapter(
                id = "chap_net1_1",
                title = "Layered Architecture",
                lessons = listOf(
                  Lesson("les_net1", "The 7-Layer OSI Model vs 4-Layer TCP/IP", 25, "Physical, Data Link, Network, Transport, and Application duties.", true),
                  Lesson("les_net2", "IPv4 vs IPv6 Addressing & Subnetting Basics", 30, "CIDR notation, subnet masks, and network vs host bits.", true)
                )
              ),
              Chapter(
                id = "chap_net1_2",
                title = "Hardware & Addressing Services",
                lessons = listOf(
                  Lesson("les_net3", "Routers, Switches, Access Points & Modems", 22, "Layer 2 MAC frame forwarding vs Layer 3 packet routing.", false),
                  Lesson("les_net4", "DNS, DHCP & Default Gateways in Action", 25, "Name resolution queries and dynamic IP leasing handshakes.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_net2",
            title = "Protocols & Diagnostics",
            chapters = listOf(
              Chapter(
                id = "chap_net2_1",
                title = "Transport & Application Layer",
                lessons = listOf(
                  Lesson("les_net5", "TCP vs UDP: Reliability vs Real-Time Speed", 20, "SYN/ACK 3-way handshakes versus connectionless datagrams.", false),
                  Lesson("les_net6", "HTTP/HTTPS, SSH, FTP & TLS Handshakes", 25, "Port numbers, symmetric session keys, and certificates.", false)
                )
              ),
              Chapter(
                id = "chap_net2_2",
                title = "Troubleshooting",
                lessons = listOf(
                  Lesson("les_net7", "Command-Line Diagnostics: Ping, Traceroute, Netstat", 25, "Diagnosing packet drops, routing loops, and port conflicts.", false),
                  Lesson("les_net8", "Networking Troubleshooting Scenario Quiz", 20, "Solve simulated enterprise LAN outage scenarios.", false)
                )
              )
            )
          )
        )
      ),

      // =================================================================
      // 9. Microsoft Word
      // =================================================================
      Course(
        id = "course_word",
        title = "Microsoft Word",
        description = "Create professional documents, master typography, page layouts, tables, headers, footers, mail merge, and track changes.",
        level = CourseLevel.BEGINNER,
        difficulty = 1,
        lessonCount = 8,
        estimatedHours = 3.0,
        iconName = "document",
        progressPercent = 35,
        tags = listOf("Word", "Office", "Documents", "Formatting"),
        modules = listOf(
          CourseModule(
            id = "mod_wd1",
            title = "Document Design & Formatting",
            chapters = listOf(
              Chapter(
                id = "chap_wd1_1",
                title = "Text & Paragraph Styles",
                lessons = listOf(
                  Lesson("les_wd1", "Ribbon Navigation & Document Templates", 15, "Interface layout, quick access toolbar, and clean starter templates.", true),
                  Lesson("les_wd2", "Typography, Fonts, Paragraphs & Indents", 20, "Line spacing, tab stops, and bullet formatting.", true)
                )
              ),
              Chapter(
                id = "chap_wd1_2",
                title = "Structured Layouts",
                lessons = listOf(
                  Lesson("les_wd3", "Header, Footer, Page Numbers & Margins", 18, "Section breaks, alternating odd/even headers, and page borders.", false),
                  Lesson("les_wd4", "Tables, Bullet Lists & Multi-level Numbering", 20, "Cell padding, column widths, and nested numbering systems.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_wd2",
            title = "Professional Publishing & Collaboration",
            chapters = listOf(
              Chapter(
                id = "chap_wd2_1",
                title = "Visuals & Automation",
                lessons = listOf(
                  Lesson("les_wd5", "Images, Shapes & SmartArt Graphics", 18, "Text wrapping, alignment tools, and hierarchy diagrams.", false),
                  Lesson("les_wd6", "Table of Contents, Citations & References", 22, "Automated heading styles, footnotes, and bibliographies.", false)
                )
              ),
              Chapter(
                id = "chap_wd2_2",
                title = "Collaboration & Review",
                lessons = listOf(
                  Lesson("les_wd7", "Track Changes, Comments & PDF Export", 18, "Co-authoring, accepting edits, and password protection.", false),
                  Lesson("les_wd8", "Word Mastery Practical Project", 20, "Design a complete multi-page formal business report.", false)
                )
              )
            )
          )
        )
      ),

      // =================================================================
      // 10. Microsoft Excel
      // =================================================================
      Course(
        id = "course_excel",
        title = "Microsoft Excel",
        description = "Build spreadsheets, master mathematical formulas, logical functions, VLOOKUP/XLOOKUP, conditional formatting, and Pivot Tables.",
        level = CourseLevel.BEGINNER,
        difficulty = 1,
        lessonCount = 9,
        estimatedHours = 4.0,
        iconName = "spreadsheet",
        progressPercent = 50,
        tags = listOf("Excel", "Spreadsheets", "Formulas", "Data"),
        modules = listOf(
          CourseModule(
            id = "mod_ex1",
            title = "Grid Mechanics & Core Formulas",
            chapters = listOf(
              Chapter(
                id = "chap_ex1_1",
                title = "Cells & Calculations",
                lessons = listOf(
                  Lesson("les_ex1", "Workbooks, Worksheets & Cell References", 15, "Relative vs absolute references (\$A\$1) and auto-fill series.", true),
                  Lesson("les_ex2", "Basic Math: SUM, AVERAGE, COUNT, MIN/MAX", 20, "Writing reliable core arithmetic and statistical formulas.", true)
                )
              ),
              Chapter(
                id = "chap_ex1_2",
                title = "Formatting & Data Management",
                lessons = listOf(
                  Lesson("les_ex3", "Conditional Formatting & Number Styles", 20, "Color scales, data bars, and currency formatting rules.", true),
                  Lesson("les_ex4", "Sorting, Multi-criteria Filters & Excel Tables", 22, "Structured table references and automatic total rows.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_ex2",
            title = "Analysis, Lookups & Pivot Tables",
            chapters = listOf(
              Chapter(
                id = "chap_ex2_1",
                title = "Logical & Lookup Formulas",
                lessons = listOf(
                  Lesson("les_ex5", "IF, AND, OR Logical Functions", 25, "Building dynamic rule-based calculation pipelines.", false),
                  Lesson("les_ex6", "VLOOKUP & Modern XLOOKUP Mastery", 30, "Exact vs approximate lookups and two-way table searching.", false)
                )
              ),
              Chapter(
                id = "chap_ex2_2",
                title = "Charts & Pivot Dashboards",
                lessons = listOf(
                  Lesson("les_ex7", "Charts, Histograms & Trendlines", 20, "Creating professional bar, line, and pie chart presentations.", false),
                  Lesson("les_ex8", "Pivot Tables & Pivot Charts from Scratch", 28, "Summarizing thousands of rows in seconds without code.", false),
                  Lesson("les_ex9", "Financial Model Capstone Project", 25, "Build an interactive automated personal finance tracker.", false)
                )
              )
            )
          )
        )
      ),

      // =================================================================
      // 11. Microsoft PowerPoint
      // =================================================================
      Course(
        id = "course_powerpoint",
        title = "Microsoft PowerPoint",
        description = "Design modern presentation decks, slide masters, typography contrast, SmartArt infographics, transitions, and presenter mode.",
        level = CourseLevel.BEGINNER,
        difficulty = 1,
        lessonCount = 7,
        estimatedHours = 2.5,
        iconName = "presentation",
        progressPercent = 15,
        tags = listOf("PowerPoint", "Slides", "Presentation", "Design"),
        modules = listOf(
          CourseModule(
            id = "mod_pp1",
            title = "Slide Architecture & Aesthetics",
            chapters = listOf(
              Chapter(
                id = "chap_pp1_1",
                title = "Templates & Typography",
                lessons = listOf(
                  Lesson("les_pp1", "Slide Masters, Themes & Color Palettes", 15, "Setting up unified fonts and background templates.", true),
                  Lesson("les_pp2", "Typography Hierarchy & Visual Contrast", 18, "Heading rules, bullet brevity, and avoiding text clutter.", false)
                )
              ),
              Chapter(
                id = "chap_pp1_2",
                title = "Visual Components",
                lessons = listOf(
                  Lesson("les_pp3", "Icons, High-Res Images & Alignment Grids", 20, "Smart guides, aspect ratios, and visual balance.", false),
                  Lesson("les_pp4", "SmartArt, Diagram Flowcharts & Infographics", 22, "Transforming complex bullet points into readable graphics.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_pp2",
            title = "Delivery, Motion & Pitching",
            chapters = listOf(
              Chapter(
                id = "chap_pp2_1",
                title = "Animation & Pacing",
                lessons = listOf(
                  Lesson("les_pp5", "Subtle Transitions & Motion Path Animations", 20, "Morph transition tricks and professional motion timing.", false),
                  Lesson("les_pp6", "Presenter View, Notes & Rehearsing Timings", 18, "Speaker cues, slide zooming, and timing controls.", false)
                )
              ),
              Chapter(
                id = "chap_pp2_2",
                title = "Final Pitch Deck",
                lessons = listOf(
                  Lesson("les_pp7", "Final Pitch Deck Creation & Review", 20, "Craft a complete 10-slide executive business pitch deck.", false)
                )
              )
            )
          )
        )
      ),

      // =================================================================
      // 12. Programming Basics
      // =================================================================
      Course(
        id = "course_programming",
        title = "Programming Fundamentals",
        description = "Core logic of computer science: variables, data types, conditional branching, loops, functions, data structures, and debugging.",
        level = CourseLevel.INTERMEDIATE,
        difficulty = 2,
        lessonCount = 8,
        estimatedHours = 4.0,
        iconName = "code",
        progressPercent = 60,
        tags = listOf("Programming", "Logic", "Algorithms", "CS"),
        modules = listOf(
          CourseModule(
            id = "mod_pr1",
            title = "Logic, Variables & Control Flow",
            chapters = listOf(
              Chapter(
                id = "chap_pr1_1",
                title = "Computational Thinking",
                lessons = listOf(
                  Lesson("les_pr1", "Algorithms, Flowcharts & Computational Thinking", 20, "Decomposing complex problems into step-by-step logic.", true),
                  Lesson("les_pr2", "Data Types: Integers, Floats, Booleans & Strings", 20, "Memory representation and strong vs loose typing.", true)
                )
              ),
              Chapter(
                id = "chap_pr1_2",
                title = "Branching & Iteration",
                lessons = listOf(
                  Lesson("les_pr3", "Conditional Statements (If, Else-If, Switch)", 22, "Boolean evaluation, relational logic, and truth tables.", true),
                  Lesson("les_pr4", "Iteration Loops (For, While, Do-While)", 25, "Loop counters, break/continue, and avoiding infinite loops.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_pr2",
            title = "Modularity & Architecture",
            chapters = listOf(
              Chapter(
                id = "chap_pr2_1",
                title = "Functions & Data Structures",
                lessons = listOf(
                  Lesson("les_pr5", "Functions, Parameters & Return Values", 22, "Scope, call stack frames, and pure functions.", false),
                  Lesson("les_pr6", "Arrays, Lists & Hash Maps / Dictionaries", 25, "Sequential indexing, key-value hashing, and O(1) lookups.", false)
                )
              ),
              Chapter(
                id = "chap_pr2_2",
                title = "Debugging & OOP Intro",
                lessons = listOf(
                  Lesson("les_pr7", "Debugging, Stack Traces & Exception Handling", 25, "Breakpoints, step-over/into, and try-catch safety.", false),
                  Lesson("les_pr8", "Object-Oriented Programming (OOP) Essentials", 25, "Classes, objects, encapsulation, and methods.", false)
                )
              )
            )
          )
        )
      ),

      // =================================================================
      // 13. Databases
      // =================================================================
      Course(
        id = "course_sql",
        title = "Database & SQL",
        description = "Relational database concepts, SQL queries (SELECT, JOIN, GROUP BY), table design, foreign keys, normalization, and ACID transactions.",
        level = CourseLevel.INTERMEDIATE,
        difficulty = 2,
        lessonCount = 8,
        estimatedHours = 4.5,
        iconName = "database",
        progressPercent = 20,
        tags = listOf("SQL", "Database", "RDBMS", "Queries"),
        modules = listOf(
          CourseModule(
            id = "mod_sql1",
            title = "Relational Database Architecture",
            chapters = listOf(
              Chapter(
                id = "chap_sql1_1",
                title = "RDBMS & Basic Queries",
                lessons = listOf(
                  Lesson("les_sql1", "Relational Concepts: Tables, Primary & Foreign Keys", 20, "Entity relationships: 1-to-1, 1-to-many, and many-to-many.", true),
                  Lesson("les_sql2", "SELECT, WHERE, ORDER BY & LIMIT Queries", 22, "Filtering rows, pattern matching with LIKE, and pagination.", false)
                )
              ),
              Chapter(
                id = "chap_sql1_2",
                title = "Aggregations & Joins",
                lessons = listOf(
                  Lesson("les_sql3", "Aggregations: GROUP BY, HAVING, COUNT & SUM", 25, "Computing summary metrics across categories.", false),
                  Lesson("les_sql4", "Multi-Table JOINS: INNER, LEFT, RIGHT & FULL", 30, "Combining related tables using join conditions.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_sql2",
            title = "Database Design & Optimization",
            chapters = listOf(
              Chapter(
                id = "chap_sql2_1",
                title = "Schema & Transactions",
                lessons = listOf(
                  Lesson("les_sql5", "INSERT, UPDATE, DELETE & Transactions (ACID)", 25, "Commit, rollback, and data integrity guarantees.", false),
                  Lesson("les_sql6", "Database Normalization (1NF, 2NF, 3NF)", 28, "Eliminating data redundancy and update anomalies.", false)
                )
              ),
              Chapter(
                id = "chap_sql2_2",
                title = "Indexing & Performance",
                lessons = listOf(
                  Lesson("les_sql7", "B-Tree Indexes, Execution Plans & Performance", 28, "Speeding up lookups with composite indexes and EXPLAIN.", false),
                  Lesson("les_sql8", "SQL Database Design Capstone Assessment", 25, "Architect an e-commerce order management schema.", false)
                )
              )
            )
          )
        )
      ),

      // =================================================================
      // 14. Cyber Security
      // =================================================================
      Course(
        id = "course_cyber",
        title = "Cybersecurity Fundamentals",
        description = "Understand the CIA triad, network threats, malware types, symmetric/asymmetric encryption, OWASP Top 10, penetration testing, and defense.",
        level = CourseLevel.ADVANCED,
        difficulty = 3,
        lessonCount = 9,
        estimatedHours = 5.5,
        iconName = "shield",
        progressPercent = 10,
        tags = listOf("Cybersecurity", "Security", "Encryption", "OWASP"),
        modules = listOf(
          CourseModule(
            id = "mod_cyb1",
            title = "Threat Landscape & Cryptography",
            chapters = listOf(
              Chapter(
                id = "chap_cyb1_1",
                title = "Security Principles",
                lessons = listOf(
                  Lesson("les_cyb1", "The CIA Triad: Confidentiality, Integrity, Availability", 20, "Foundational pillars of information security architecture.", true),
                  Lesson("les_cyb2", "Malware Types: Ransomware, Trojans, Rootkits & Worms", 25, "Infection vectors, payload execution, and command-and-control.", false)
                )
              ),
              Chapter(
                id = "chap_cyb1_2",
                title = "Cryptographic Foundations",
                lessons = listOf(
                  Lesson("les_cyb3", "Symmetric vs Asymmetric Encryption (AES & RSA)", 30, "Shared secrets versus public-private key pair mathematics.", false),
                  Lesson("les_cyb4", "Hashing (SHA-256), Salts & Digital Signatures", 28, "One-way cryptographic functions, collision resistance, and HMAC.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_cyb2",
            title = "Attack Vectors & Enterprise Defense",
            chapters = listOf(
              Chapter(
                id = "chap_cyb2_1",
                title = "Web & Network Attacks",
                lessons = listOf(
                  Lesson("les_cyb5", "Man-in-the-Middle (MitM) & DNS Spoofing Attacks", 28, "ARP cache poisoning and rogue Wi-Fi access points.", false),
                  Lesson("les_cyb6", "OWASP Top 10: SQL Injection, XSS & CSRF", 32, "Input sanitization, parameterized queries, and CSP headers.", false)
                )
              ),
              Chapter(
                id = "chap_cyb2_2",
                title = "Defense & Incident Response",
                lessons = listOf(
                  Lesson("les_cyb7", "Zero Trust Architecture & Multi-Factor Auth (MFA)", 25, "Never trust, always verify: contextual access and FIDO2.", false),
                  Lesson("les_cyb8", "Penetration Testing Methodology & Ethical Hacking", 30, "Reconnaissance, vulnerability scanning, and exploitation.", false),
                  Lesson("les_cyb9", "Cyber Incident Response & Forensic Analysis", 28, "Triage, containment, eradication, and post-incident review.", false)
                )
              )
            )
          )
        )
      ),

      // =================================================================
      // 15. Cloud Computing
      // =================================================================
      Course(
        id = "course_cloud",
        title = "Cloud Computing",
        description = "Understand IaaS, PaaS, SaaS, AWS/GCP/Azure architecture, Virtual Machines, S3 buckets, VPC networking, IAM security, and Docker containers.",
        level = CourseLevel.ADVANCED,
        difficulty = 3,
        lessonCount = 9,
        estimatedHours = 5.5,
        iconName = "cloud",
        progressPercent = 25,
        tags = listOf("Cloud", "AWS", "Docker", "DevOps"),
        modules = listOf(
          CourseModule(
            id = "mod_cld1",
            title = "Cloud Paradigms & Core Services",
            chapters = listOf(
              Chapter(
                id = "chap_cld1_1",
                title = "Cloud Architecture",
                lessons = listOf(
                  Lesson("les_cld1", "Cloud Models: IaaS, PaaS, SaaS & Hybrid Clouds", 22, "Shared responsibility model and economic cost models (OpEx).", true),
                  Lesson("les_cld2", "Regions, Availability Zones & Global Infrastructure", 22, "High availability, fault tolerance, and latency zones.", true)
                )
              ),
              Chapter(
                id = "chap_cld1_2",
                title = "Compute & Storage",
                lessons = listOf(
                  Lesson("les_cld3", "Virtual Machines (EC2/GCE) vs Serverless Functions", 30, "Elastic compute provisioning, auto-scaling, and event triggers.", false),
                  Lesson("les_cld4", "Object Storage (S3/Cloud Storage) & Lifecycle Policies", 25, "Buckets, versioning, storage tiers (Glacier), and CORS.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_cld2",
            title = "Networking, Security & Containers",
            chapters = listOf(
              Chapter(
                id = "chap_cld2_1",
                title = "Cloud Networks & IAM",
                lessons = listOf(
                  Lesson("les_cld5", "Virtual Private Clouds (VPC), Subnets & Gateways", 30, "Public/private subnets, NAT gateways, and security groups.", false),
                  Lesson("les_cld6", "Identity & Access Management (IAM) Roles & Policies", 28, "Least privilege principle, role assumption, and policy JSON.", false)
                )
              ),
              Chapter(
                id = "chap_cld2_2",
                title = "Containers & Kubernetes",
                lessons = listOf(
                  Lesson("les_cld7", "Docker Containers: Images, Dockerfile & Volumes", 30, "Containerizing apps for consistent execution environments.", false),
                  Lesson("les_cld8", "Kubernetes Basics: Pods, Services & Deployments", 32, "Container orchestration, rolling updates, and self-healing.", false),
                  Lesson("les_cld9", "Designing a Highly Available Cloud Architecture", 30, "Architecting a multi-tier resilient cloud application.", false)
                )
              )
            )
          )
        )
      ),

      // =================================================================
      // 16. Artificial Intelligence
      // =================================================================
      Course(
        id = "course_ai",
        title = "Artificial Intelligence",
        description = "Machine learning principles, neural networks, computer vision, natural language processing, Large Language Models, and prompt engineering.",
        level = CourseLevel.ADVANCED,
        difficulty = 3,
        lessonCount = 9,
        estimatedHours = 6.0,
        iconName = "ai",
        progressPercent = 20,
        isBookmarked = true,
        tags = listOf("AI", "MachineLearning", "LLMs", "NeuralNets"),
        modules = listOf(
          CourseModule(
            id = "mod_ai1",
            title = "Foundations of Machine Learning",
            chapters = listOf(
              Chapter(
                id = "chap_ai1_1",
                title = "AI & ML Paradigms",
                lessons = listOf(
                  Lesson("les_ai1", "History of AI: From Turing to Deep Learning", 22, "Turing tests, symbolic AI, AI winters, and deep learning breakthrough.", true),
                  Lesson("les_ai2", "Supervised, Unsupervised & Reinforcement Learning", 28, "Classification, regression, clustering, and reward policies.", true)
                )
              ),
              Chapter(
                id = "chap_ai1_2",
                title = "Neural Networks",
                lessons = listOf(
                  Lesson("les_ai3", "Perceptrons, Weights, Biases & Activation Functions", 32, "ReLU, Sigmoid, Softmax, and mathematical neuron models.", false),
                  Lesson("les_ai4", "Backpropagation, Loss Functions & Gradient Descent", 35, "Optimizing neural weights via matrix calculus and learning rates.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_ai2",
            title = "Transformers, LLMs & Practical AI",
            chapters = listOf(
              Chapter(
                id = "chap_ai2_1",
                title = "Transformers & LLMs",
                lessons = listOf(
                  Lesson("les_ai5", "Transformer Architecture & Self-Attention Mechanisms", 32, "The 2017 breakthrough that sparked the generative AI revolution.", false),
                  Lesson("les_ai6", "How LLMs Work: Tokens, Embeddings & Generation", 30, "Vector spaces, cosine similarity, temperature, and top-p sampling.", false),
                  Lesson("les_ai7", "Advanced Prompt Engineering & Chain-of-Thought", 28, "Few-shot prompting, system instructions, and structured output.", false)
                )
              ),
              Chapter(
                id = "chap_ai2_2",
                title = "Applied AI & Ethics",
                lessons = listOf(
                  Lesson("les_ai8", "Retrieval-Augmented Generation (RAG) Architectures", 32, "Connecting LLMs to private vector databases and live docs.", false),
                  Lesson("les_ai9", "AI Alignment, Safety, Bias & The Future of AGI", 26, "Responsible AI development, hallucination mitigation, and alignment.", false)
                )
              )
            )
          )
        )
      ),

      // =================================================================
      // 17. Computer Troubleshooting
      // =================================================================
      Course(
        id = "course_troubleshooting",
        title = "JavaScript",
        description = "Learn JavaScript variables, data types, conditions, loops, functions, arrays, objects, DOM events, forms, and browser debugging.",
        level = CourseLevel.INTERMEDIATE,
        difficulty = 2,
        lessonCount = 0,
        estimatedHours = 0.0,
        iconName = "javascript",
        progressPercent = 0,
        isBookmarked = false,
        tags = listOf("JavaScript", "Web", "DOM", "Events", "Programming"),
        modules = emptyList()
      ),

      // =================================================================
      // 18. Advanced Computer Knowledge
      // =================================================================
      Course(
        id = "course_it",
        title = "Advanced IT",
        description = "Enterprise IT administration: Active Directory, Group Policy, LDAP, enterprise virtualization (ESXi/Proxmox), disaster recovery, and RAID.",
        level = CourseLevel.ADVANCED,
        difficulty = 3,
        lessonCount = 8,
        estimatedHours = 5.0,
        iconName = "server",
        progressPercent = 10,
        tags = listOf("Enterprise", "ActiveDirectory", "RAID", "Virtualization"),
        modules = listOf(
          CourseModule(
            id = "mod_it1",
            title = "Enterprise Infrastructure & Identity",
            chapters = listOf(
              Chapter(
                id = "chap_it1_1",
                title = "Directory Services",
                lessons = listOf(
                  Lesson("les_it1", "Active Directory, Domain Controllers & LDAP", 28, "Domain forests, Kerberos authentication, and organizational units.", true),
                  Lesson("les_it2", "Group Policy Objects (GPO) Enterprise Management", 26, "Centrally enforcing security baselines across corporate fleets.", false)
                )
              ),
              Chapter(
                id = "chap_it1_2",
                title = "Virtualization & Storage",
                lessons = listOf(
                  Lesson("les_it3", "Type-1 Hypervisors: VMware ESXi, Proxmox & Hyper-V", 30, "Bare-metal hypervisor architecture and hardware passthrough.", false),
                  Lesson("les_it4", "SAN, NAS, RAID Levels (0, 1, 5, 10) & iSCSI", 28, "Parity calculations, hot-spares, and storage networks.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_it2",
            title = "Reliability & IT Operations",
            chapters = listOf(
              Chapter(
                id = "chap_it2_1",
                title = "Disaster Recovery",
                lessons = listOf(
                  Lesson("les_it5", "The 3-2-1 Backup Strategy & RTO/RPO Calculations", 25, "Three copies, two media types, one offsite, and recovery objectives.", false),
                  Lesson("les_it6", "Hardware Diagnostics: Memory Tests, SMART & PSU Rails", 25, "MemTest86, CrystalDisk, and PSU rail voltage troubleshooting.", false)
                )
              ),
              Chapter(
                id = "chap_it2_2",
                title = "ITIL & Capstone",
                lessons = listOf(
                  Lesson("les_it7", "ITIL Framework: Incident, Problem & Change Management", 22, "SLA tiers, escalation paths, and root cause post-mortems.", false),
                  Lesson("les_it8", "Enterprise Network Disaster Recovery Simulation", 30, "Resolve multi-tier enterprise network outage simulations.", false)
                )
              )
            )
          )
        )
      )

    )

    val populatedCourses = definedCourses.map { course ->
      val modules = if (course.modules.isNotEmpty()) course.modules else createCourseModules(course.id, course.title)
      val count = modules.sumOf { module -> module.lessons.size }
      course.copy(
        modules = modules,
        lessonCount = count,
        estimatedHours = if (course.estimatedHours > 0.0) course.estimatedHours
          else modules.flatMap { it.lessons }.sumOf { it.durationMinutes } / 60.0,
        category = when (course.id) {
          "course_basics" -> "Computer Basics"
          "course_hardware" -> "Keyboard & Typing"
          "course_software" -> "Programming"
          "course_os" -> "Linux"
          "course_windows" -> "Windows & Files"
          "course_files" -> "App Development"
          "course_internet" -> "Internet & Email"
          "course_networking" -> "Networking"
          "course_word", "course_excel", "course_powerpoint" -> "Office & Productivity"
          "course_programming", "course_python" -> "Programming"
          "course_sql" -> "Database & SQL"
          "course_cyber" -> "Cybersecurity"
          "course_cloud" -> "Cloud Computing"
          "course_ai" -> "Artificial Intelligence"
          "course_troubleshooting" -> "JavaScript"
          "course_it" -> "Advanced IT"
          "course_webdev" -> "Web Development"
          else -> course.category
        }
      )
    }

    val additionalCourses = listOf(
      createSupplementalCourse(
        id = "course_python",
        title = "Python Programming",
        description = "Learn Python from first program through variables, conditions, loops, functions, collections, files, exceptions, and a practical mini-project.",
        level = CourseLevel.INTERMEDIATE,
        difficulty = 2,
        iconName = "code",
        tags = listOf("Python", "Programming", "Automation"),
        topics = listOf(
          "Install Python and run your first program" to "Understand the interpreter, print(), comments, scripts, and readable indentation.",
          "Variables, data types, and input" to "Use strings, integers, floats, booleans, input(), and type conversion.",
          "Operators and expressions" to "Use arithmetic, comparison, logical, assignment, and membership operators.",
          "Conditions and decisions" to "Control program flow with if, elif, else, and clear boolean logic.",
          "Loops and repetition" to "Use for, while, range(), break, and continue while avoiding infinite loops.",
          "Functions and reusable code" to "Define functions, pass parameters, return values, and organise tasks.",
          "Lists, tuples, dictionaries, and sets" to "Store, access, update, and iterate over collections of information.",
          "Files, exceptions, and mini-project" to "Read and write text files, handle errors, debug tracebacks, and build a small command-line project."
        )
      ),
      createSupplementalCourse(
        id = "course_webdev",
        title = "Web Development",
        description = "Build websites step by step with HTML, CSS, responsive layouts, JavaScript interactions, accessibility, browser tools, and publishing basics.",
        level = CourseLevel.INTERMEDIATE,
        difficulty = 2,
        iconName = "web",
        tags = listOf("HTML", "CSS", "JavaScript", "Responsive Design"),
        topics = listOf(
          "How websites and the web work" to "Learn browsers, servers, URLs, requests, domains, hosting, and the roles of HTML, CSS, and JavaScript.",
          "HTML structure and semantic elements" to "Create headings, paragraphs, links, lists, images, forms, and accessible page sections.",
          "CSS selectors, colours, and typography" to "Style pages using selectors, spacing, fonts, colours, borders, and reusable rules.",
          "Layout with Flexbox and Grid" to "Arrange sections, manage alignment, and create balanced page layouts.",
          "Responsive design for mobile screens" to "Use relative units, media queries, and mobile-first layouts for different screens.",
          "JavaScript basics for web pages" to "Work with variables, functions, conditions, arrays, events, and the DOM.",
          "Forms, validation, and accessibility" to "Build usable forms, validate input, label controls, and support keyboard navigation.",
          "Testing and publishing a website" to "Use developer tools, check responsive layouts, optimise assets, and understand static hosting."
        )
      )
    )
    val allCourses = populatedCourses + additionalCourses
    val learningPathOrder = listOf(
      "course_basics", "course_windows", "course_hardware", "course_word",
      "course_excel", "course_powerpoint", "course_internet", "course_networking",
      "course_programming", "course_python", "course_software", "course_troubleshooting",
      "course_webdev", "course_sql", "course_files", "course_os", "course_cyber",
      "course_cloud", "course_it", "course_ai"
    )
    return allCourses.sortedBy { course -> learningPathOrder.indexOf(course.id).let { if (it < 0) Int.MAX_VALUE else it } }
  }

  private fun createCourseModules(courseId: String, courseTitle: String): List<CourseModule> {
    val topics = when (courseTitle) {
      "Keyboard & Typing" -> listOf(
        "Motherboard and internal components" to "Identify the motherboard, CPU socket, chipset, memory slots, expansion slots, and component communication.",
        "CPU cores and performance" to "Understand cores, clock speed, cache, thermal limits, and everyday performance.",
        "RAM and memory installation" to "Learn memory capacity, DIMMs, channels, compatibility, and safe installation.",
        "HDD, SSD, and NVMe storage" to "Compare storage technologies, speed, capacity, durability, and backup needs.",
        "Power supply and electrical safety" to "Understand PSU ratings, connectors, surge protection, and safe servicing.",
        "Cooling, fans, and airflow" to "Learn heatsinks, thermal paste, fan direction, dust removal, and overheating signs.",
        "Ports, cables, and expansion cards" to "Identify USB, HDMI, DisplayPort, Ethernet, audio, and PCIe connections.",
        "Hardware diagnosis checklist" to "Check component compatibility and diagnose common no-power or no-display symptoms."
      )
      "C/C++ Programming" -> listOf(
        "Software types and applications" to "Distinguish operating systems, apps, utilities, and the instructions a computer executes.",
        "Operating systems and drivers" to "Understand how an OS manages hardware and why trusted compatible drivers matter.",
        "Productivity applications" to "Choose tools for documents, spreadsheets, presentations, communication, and media.",
        "Safe installation and removal" to "Use official sources, review permissions, and uninstall apps cleanly.",
        "File formats and compatibility" to "Recognise common extensions, supported formats, versions, and safe conversion.",
        "Licences and open-source software" to "Compare freeware, commercial licences, subscriptions, and open-source distribution.",
        "Security and maintenance utilities" to "Use reputable security, backup, and maintenance tools safely.",
        "Fixing software problems" to "Capture errors, check updates and permissions, and repair apps step by step."
      )
      "Linux Fundamentals" -> listOf(
        "Purpose of an operating system" to "Learn how an OS provides an interface and manages computer resources.",
        "Kernel, processes, and multitasking" to "Understand processes, threads, scheduling, and running multiple apps.",
        "Memory management and virtual memory" to "Learn RAM allocation, paging, swap space, and memory pressure.",
        "Filesystems and permissions" to "Understand paths, filesystem organisation, users, and access permissions.",
        "Accounts and system security" to "Compare standard and administrator accounts, authentication, updates, and least privilege.",
        "Devices, drivers, and services" to "Learn how an OS communicates with hardware and manages background services.",
        "Updates and recovery" to "Plan updates, backups, recovery media, and restore points.",
        "Comparing Windows, Linux, Android, and macOS" to "Compare common operating systems, their interfaces, and use cases."
      )
      "App Development" -> listOf(
        "Files, folders, and paths" to "Understand files, directories, root locations, and folder hierarchies.",
        "Create, rename, copy, move, and delete" to "Practise safe file operations and distinguish copying from moving.",
        "File extensions and formats" to "Recognise document, image, audio, video, archive, and executable extensions.",
        "Search and file organisation" to "Find files by name, type, date, and location, and use consistent naming.",
        "ZIP archives and compression" to "Create and extract archives while avoiding suspicious executables.",
        "Cloud storage and external drives" to "Compare local storage, USB drives, cloud sync, and backups.",
        "Sharing and permissions" to "Share intended files only, review link access, and protect private information.",
        "File recovery and data safety" to "Use recycle bins, version history, backups, and careful deletion."
      )
      "JavaScript" -> listOf(
        "A safe troubleshooting method" to "Record symptoms, reproduce problems safely, change one thing at a time, and document results.",
        "Computer will not power on or boot" to "Check power, cables, indicators, peripherals, and boot messages safely.",
        "Slow computer and high resource usage" to "Inspect CPU, memory, storage, and startup applications with system monitors.",
        "Overheating, noise, and shutdowns" to "Recognise blocked airflow, dust, fan problems, and signs needing service.",
        "Internet and Wi-Fi problems" to "Check Wi-Fi, router status, IP settings, and basic connectivity step by step.",
        "Application crashes and update errors" to "Capture error messages, check compatibility and storage, and use official repair options.",
        "Malware warnings and suspicious activity" to "Avoid unknown downloads, scan with trusted tools, and protect accounts.",
        "Backups, recovery, and technical support" to "Protect files, use recovery options carefully, and prepare details for support."
      )
      "Keyboard & Typing" -> listOf(
        "Keyboard layout and key groups" to "Identify letter keys, number row, function keys, navigation keys, modifiers, and the numeric keypad.",
        "Correct posture and finger placement" to "Sit comfortably, keep wrists relaxed, place fingers on the home row, and avoid unnecessary strain.",
        "Typing without looking at the keyboard" to "Practise home-row reach, use all fingers gradually, and prioritise accuracy before speed.",
        "Shift, Caps Lock, and punctuation" to "Use Shift for capital letters and symbols, understand Caps Lock, and practise punctuation carefully.",
        "Essential keyboard shortcuts" to "Use common shortcuts such as Ctrl+C, Ctrl+V, Ctrl+Z, Ctrl+S, Alt+Tab, and their platform equivalents.",
        "Typing numbers and special characters" to "Practise number rows, symbols, punctuation, and switching between language layouts.",
        "Measure speed and accuracy" to "Use short timed passages, track words per minute and error rate, and set realistic goals.",
        "Daily typing practice plan" to "Follow short, regular practice sessions, review recurring mistakes, and increase speed only as accuracy improves."
      )
      "C/C++ Programming" -> listOf(
        "C and C++ setup and first program" to "Understand compiler versus interpreter, source files, main(), headers, and printing output.",
        "Variables, types, and operators" to "Use integers, floating-point values, characters, booleans, expressions, and safe type conversions.",
        "Input and output" to "Read user input, display results, validate values, and format output clearly.",
        "Conditions and decision-making" to "Use if, else if, else, switch, comparisons, and logical operators.",
        "Loops and repetition" to "Use for, while, and do-while loops, control loop execution, and avoid off-by-one errors.",
        "Functions and modular code" to "Declare functions, pass parameters, return values, and divide programs into reusable units.",
        "Arrays, strings, and collections" to "Store sequences, access elements safely, and understand basic string operations.",
        "Pointers, references, and a mini-project" to "Learn the basics of addresses and references, practise debugging, and build a small console program."
      )
      "Linux Fundamentals" -> listOf(
        "Linux distributions and desktop" to "Understand the Linux kernel, distributions, desktop environments, and choosing a trusted distribution.",
        "Files, folders, and paths" to "Navigate absolute and relative paths and understand the Linux directory hierarchy.",
        "Essential terminal commands" to "Practise pwd, ls, cd, mkdir, cp, mv, cat, and man in a safe test folder.",
        "Users, groups, and permissions" to "Understand ownership, read/write/execute permissions, and least-privilege access.",
        "Packages and software updates" to "Use the distribution's trusted package manager and apply security updates.",
        "Processes and system monitoring" to "Inspect running processes, resource usage, services, and logs with standard tools.",
        "Networking and SSH basics" to "Check network status, understand IP addresses, and use secure remote access concepts.",
        "Shell scripting and safe administration" to "Combine commands into simple scripts, inspect commands before running them, and avoid unsafe privileged commands."
      )
      "App Development" -> listOf(
        "How mobile apps are built" to "Compare native, cross-platform, and web apps, and understand screens, state, and app lifecycle.",
        "Plan screens and user journeys" to "Sketch the main screen, navigation, user tasks, empty states, and accessible controls.",
        "UI components and responsive layouts" to "Use consistent typography, spacing, buttons, cards, and layouts that adapt to screen sizes.",
        "State, events, and navigation" to "Handle taps, forms, screen transitions, loading states, and user feedback.",
        "Local data and preferences" to "Choose suitable local storage for settings, notes, and structured records while protecting user data.",
        "Connect to APIs safely" to "Understand requests and responses, handle loading/errors, and never embed private secrets in an app.",
        "Testing, accessibility, and performance" to "Test common screen sizes, touch targets, error cases, accessibility, and startup performance.",
        "Build, sign, and publish an app" to "Understand versioning, signing keys, release notes, APK/AAB builds, and safe update delivery."
      )
      "JavaScript" -> listOf(
        "JavaScript and the browser" to "Understand where JavaScript runs and how it adds behaviour to HTML pages.",
        "Variables, values, and types" to "Use const and let, strings, numbers, booleans, null, undefined, and type conversion.",
        "Operators and conditions" to "Use comparisons, logical operators, if/else, and switch to make decisions.",
        "Loops and functions" to "Repeat work safely and create reusable functions with parameters and return values.",
        "Arrays and objects" to "Store lists and structured records, access properties, and use common array methods.",
        "DOM selection and events" to "Select page elements, respond to clicks and input, and update content accessibly.",
        "Forms, validation, and asynchronous work" to "Validate user input and understand promises, async/await, and fetch error handling.",
        "Debugging and a mini-project" to "Use browser developer tools, read console errors, and build a small interactive page."
      )
            else -> listOf(
        "$courseTitle: key concepts" to "Learn the purpose, terminology, and everyday uses of $courseTitle.",
        "$courseTitle: tools and workspace" to "Identify common tools, interface elements, and safe setup practices.",
        "$courseTitle: essential workflow" to "Follow a beginner-friendly workflow and understand the main steps.",
        "$courseTitle: practical examples" to "Connect core ideas to realistic tasks and everyday computer use.",
        "$courseTitle: common errors" to "Recognise frequent mistakes and learn how to avoid them.",
        "$courseTitle: useful shortcuts and tips" to "Practise efficient habits, shortcuts, and organised working methods.",
        "$courseTitle: guided practice" to "Apply the lesson in a small task and check the result against clear criteria.",
        "$courseTitle: review and next steps" to "Summarise key points and prepare for the next lesson."
      )
    }
    val prefix = courseId.removePrefix("course_")
    return listOf(
      CourseModule(
        id = "mod_${prefix}_1",
        title = "Fundamentals",
        chapters = listOf(Chapter(
          id = "chap_${prefix}_1",
          title = "Core Concepts",
          lessons = topics.take(4).mapIndexed { index, topic ->
            Lesson("${prefix}_lesson_${index + 1}", topic.first, 12 + index * 2, topic.second)
          }
        ))
      ),
      CourseModule(
        id = "mod_${prefix}_2",
        title = "Practice and Skills",
        chapters = listOf(Chapter(
          id = "chap_${prefix}_2",
          title = "Practical Skills",
          lessons = topics.drop(4).mapIndexed { index, topic ->
            Lesson("${prefix}_lesson_${index + 5}", topic.first, 12 + index * 2, topic.second)
          }
        ))
      )
    )
  }

  private fun createSupplementalCourse(
    id: String,
    title: String,
    description: String,
    level: CourseLevel,
    difficulty: Int,
    iconName: String,
    tags: List<String>,
    topics: List<Pair<String, String>>
  ): Course {
    val prefix = id.removePrefix("course_")
    val modules = listOf(
      CourseModule(
        id = "mod_${prefix}_1",
        title = "Fundamentals",
        chapters = listOf(Chapter(
          id = "chap_${prefix}_1",
          title = "Core Concepts",
          lessons = topics.take(4).mapIndexed { index, topic ->
            Lesson("${prefix}_lesson_${index + 1}", topic.first, 14 + index * 2, topic.second)
          }
        ))
      ),
      CourseModule(
        id = "mod_${prefix}_2",
        title = "Practice and Projects",
        chapters = listOf(Chapter(
          id = "chap_${prefix}_2",
          title = "Build Your Skills",
          lessons = topics.drop(4).mapIndexed { index, topic ->
            Lesson("${prefix}_lesson_${index + 5}", topic.first, 14 + index * 2, topic.second)
          }
        ))
      )
    )
    return Course(
      id = id,
      title = title,
      description = description,
      level = level,
      difficulty = difficulty,
      lessonCount = topics.size,
      estimatedHours = modules.flatMap { it.lessons }.sumOf { it.durationMinutes } / 60.0,
      iconName = iconName,
      tags = tags,
      category = "Programming & Development",
      modules = modules
    )
  }
}