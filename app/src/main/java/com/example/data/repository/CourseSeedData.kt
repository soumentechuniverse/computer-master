package com.example.data.repository

import com.example.data.model.Chapter
import com.example.data.model.Course
import com.example.data.model.CourseLevel
import com.example.data.model.CourseModule
import com.example.data.model.Lesson

object CourseSeedData {

  fun getInitialCourses(): List<Course> {
    return listOf(
      // =================================================================
      // BEGINNER COURSES (7 COURSES)
      // =================================================================

      // 1. Computer Basics
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
        tags = listOf("Hardware", "CPU", "RAM", "Components"),
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

      // 2. Windows & File Management
      Course(
        id = "course_windows",
        title = "Windows & File Management",
        description = "Master File Explorer, folder hierarchy, extensions, system settings, Task Manager, and keyboard shortcuts for maximum speed.",
        level = CourseLevel.BEGINNER,
        difficulty = 1,
        lessonCount = 7,
        estimatedHours = 2.5,
        iconName = "folder",
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

      // 3. Keyboard & Typing
      Course(
        id = "course_typing",
        title = "Keyboard & Typing",
        description = "Learn home row touch typing, numeric keypad speed, ergonomic posture, and master common developer and Office shortcuts.",
        level = CourseLevel.BEGINNER,
        difficulty = 1,
        lessonCount = 6,
        estimatedHours = 2.0,
        iconName = "keyboard",
        progressPercent = 75,
        tags = listOf("Typing", "Speed", "Ergonomics"),
        modules = listOf(
          CourseModule(
            id = "mod_t1",
            title = "Touch Typing Mastery",
            chapters = listOf(
              Chapter(
                id = "chap_t1_1",
                title = "Home Row & Posture",
                lessons = listOf(
                  Lesson("les_t1", "Home Row Technique (ASDF JKL;)", 15, "Finger positioning and muscle memory anchor points.", true),
                  Lesson("les_t2", "Top & Bottom Row Muscle Memory Drills", 20, "Reaching keys without looking down at the keyboard.", true)
                )
              ),
              Chapter(
                id = "chap_t1_2",
                title = "Number & Symbol Rows",
                lessons = listOf(
                  Lesson("les_t3", "Numbers, Special Symbols & Punctuation", 18, "Shift key mechanics and bracket placement.", true),
                  Lesson("les_t4", "Numeric Keypad Speed Training", 15, "10-key touch typing drills for spreadsheets and data entry.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_t2",
            title = "Speed & Shortcut Fluency",
            chapters = listOf(
              Chapter(
                id = "chap_t2_1",
                title = "Ergonomics & Benchmarks",
                lessons = listOf(
                  Lesson("les_t5", "Typing Ergonomics & Repetitive Strain Prevention", 12, "Wrist angle, chair height, and healthy micro-breaks.", false),
                  Lesson("les_t6", "Word Processing & Coding Speed Benchmarks", 20, "Measuring WPM and reducing error rates.", false)
                )
              )
            )
          )
        )
      ),

      // 4. Microsoft Word
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

      // 5. Microsoft Excel
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

      // 6. Microsoft PowerPoint
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

      // 7. Internet & Email
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
      // INTERMEDIATE COURSES (7 COURSES)
      // =================================================================

      // 8. Computer Networking
      Course(
        id = "course_networking",
        title = "Computer Networking",
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

      // 9. Programming Fundamentals
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

      // 10. Python
      Course(
        id = "course_python",
        title = "Python",
        description = "Learn modern Python from scratch: syntax, collections, list comprehensions, OOP, file handling, web scraping, and automation scripts.",
        level = CourseLevel.INTERMEDIATE,
        difficulty = 2,
        lessonCount = 9,
        estimatedHours = 5.0,
        iconName = "python",
        progressPercent = 45,
        isBookmarked = true,
        tags = listOf("Python", "Coding", "Automation", "Scripts"),
        modules = listOf(
          CourseModule(
            id = "mod_py1",
            title = "Python Core Syntax & Data Structures",
            chapters = listOf(
              Chapter(
                id = "chap_py1_1",
                title = "Foundations",
                lessons = listOf(
                  Lesson("les_py1", "Python Setup, Virtual Environments & REPL", 18, "Installing Python 3, pip, venv, and running your first script.", true),
                  Lesson("les_py2", "Variables, Dynamic Typing & F-String Formatting", 20, "String manipulation, numeric operations, and type casting.", true)
                )
              ),
              Chapter(
                id = "chap_py1_2",
                title = "Collections & Comprehensions",
                lessons = listOf(
                  Lesson("les_py3", "Lists, Tuples, Dictionaries & Sets", 25, "Mutation rules, dictionary keys, and set unions/intersections.", true),
                  Lesson("les_py4", "List Comprehensions & Generator Expressions", 25, "Writing elegant, one-line expressive data transformations.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_py2",
            title = "Advanced Python & Automation",
            chapters = listOf(
              Chapter(
                id = "chap_py2_1",
                title = "OOP & File I/O",
                lessons = listOf(
                  Lesson("les_py5", "Classes, Methods, Inheritance & Dunder Methods", 30, "__init__, __str__, and custom object representations.", false),
                  Lesson("les_py6", "Reading & Writing Text, CSV and JSON Files", 22, "Using context managers ('with' open) and json module.", false)
                )
              ),
              Chapter(
                id = "chap_py2_2",
                title = "Web Scraping & Real Projects",
                lessons = listOf(
                  Lesson("les_py7", "Error Handling & Custom Exceptions", 20, "Try, except, else, finally blocks and defensive coding.", false),
                  Lesson("les_py8", "Automating Tasks with Requests & Beautiful Soup", 30, "HTTP GET requests, HTML parsing, and data harvesting.", false),
                  Lesson("les_py9", "Building a Python CLI Automation Tool", 30, "Crafting an end-to-end command-line utility with argparse.", false)
                )
              )
            )
          )
        )
      ),

      // 11. C/C++
      Course(
        id = "course_cpp",
        title = "C/C++",
        description = "Understand low-level systems programming, pointers, memory allocation (stack vs heap), structs, OOP, templates, and the STL.",
        level = CourseLevel.INTERMEDIATE,
        difficulty = 2,
        lessonCount = 9,
        estimatedHours = 5.5,
        iconName = "compiler",
        progressPercent = 10,
        tags = listOf("C", "C++", "Pointers", "Systems"),
        modules = listOf(
          CourseModule(
            id = "mod_cpp1",
            title = "Memory & Low-Level Mechanics",
            chapters = listOf(
              Chapter(
                id = "chap_cpp1_1",
                title = "Compilation & Primitives",
                lessons = listOf(
                  Lesson("les_cpp1", "C/C++ Compilers, Preprocessor & Linkers", 22, "GCC, Clang, header files (#include), and object code.", true),
                  Lesson("les_cpp2", "Primitive Data Types, Structs & Bitwise Operations", 25, "Memory sizes, signed/unsigned types, and struct alignment.", false)
                )
              ),
              Chapter(
                id = "chap_cpp1_2",
                title = "Pointers & Memory Architecture",
                lessons = listOf(
                  Lesson("les_cpp3", "Pointers, Memory Addresses & Dereferencing", 35, "Pointer arithmetic, null pointers, and void pointers.", false),
                  Lesson("les_cpp4", "Stack vs Heap: Malloc, Free, New & Delete", 35, "Dynamic memory management and preventing memory leaks.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_cpp2",
            title = "C++ Modern Paradigms & STL",
            chapters = listOf(
              Chapter(
                id = "chap_cpp2_1",
                title = "OOP & Templates",
                lessons = listOf(
                  Lesson("les_cpp5", "Constructors, Destructors & Rule of Five", 28, "Resource Acquisition Is Initialization (RAII) idiom.", false),
                  Lesson("les_cpp6", "Operator Overloading & Function Templates", 28, "Generic programming with type-safe template expansion.", false)
                )
              ),
              Chapter(
                id = "chap_cpp2_2",
                title = "Modern C++ & STL",
                lessons = listOf(
                  Lesson("les_cpp7", "STL Containers: Vectors, Maps & Iterators", 30, "High-performance standard template library algorithms.", false),
                  Lesson("les_cpp8", "Smart Pointers: unique_ptr & shared_ptr", 25, "Automated reference counting and ownership models.", false),
                  Lesson("les_cpp9", "Building a High-Performance Data Pipeline", 30, "Low-latency binary file processor project.", false)
                )
              )
            )
          )
        )
      ),

      // 12. JavaScript
      Course(
        id = "course_javascript",
        title = "JavaScript",
        description = "Modern ECMAScript (ES6+), DOM manipulation, closures, event loop, Promises, async/await, modules, and API integrations.",
        level = CourseLevel.INTERMEDIATE,
        difficulty = 2,
        lessonCount = 8,
        estimatedHours = 4.5,
        iconName = "javascript",
        progressPercent = 50,
        tags = listOf("JavaScript", "ES6", "Async", "Web"),
        modules = listOf(
          CourseModule(
            id = "mod_js1",
            title = "Modern JS (ES6+) Syntax & Scope",
            chapters = listOf(
              Chapter(
                id = "chap_js1_1",
                title = "Core Language",
                lessons = listOf(
                  Lesson("les_js1", "Variables (let, const), Scopes & Hoisting", 20, "Temporal dead zone and block scoping mechanics.", true),
                  Lesson("les_js2", "Arrow Functions, Destructuring & Rest/Spread", 22, "Modern shorthand syntax and object manipulation.", true)
                )
              ),
              Chapter(
                id = "chap_js1_2",
                title = "Data & Closures",
                lessons = listOf(
                  Lesson("les_js3", "Array Methods: map, filter, reduce & flatMap", 28, "Declarative functional transformations on dataset arrays.", true),
                  Lesson("les_js4", "Closures, Lexical Scope & The 'this' Keyword", 30, "Function factories, private state, and execution context.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_js2",
            title = "Asynchronous JavaScript & Web APIs",
            chapters = listOf(
              Chapter(
                id = "chap_js2_1",
                title = "Promises & Event Loop",
                lessons = listOf(
                  Lesson("les_js5", "The Event Loop, Call Stack & Microtask Queue", 25, "Non-blocking I/O execution lifecycle in browser engines.", false),
                  Lesson("les_js6", "Promises, Async/Await & Error Handling", 28, "Resolving async flows without callback hell.", false)
                )
              ),
              Chapter(
                id = "chap_js2_2",
                title = "Web APIs & Projects",
                lessons = listOf(
                  Lesson("les_js7", "Fetch API, JSON Parsing & ES Modules", 25, "Connecting to cloud REST APIs and modular exports.", false),
                  Lesson("les_js8", "JavaScript Interactive Mini-App Project", 25, "Build a real-time reactive currency converter application.", false)
                )
              )
            )
          )
        )
      ),

      // 13. Web Development
      Course(
        id = "course_web",
        title = "Web Development",
        description = "Full web stack introduction: semantic HTML5, modern CSS3 Flexbox/Grid, responsive layouts, web performance, and client-server REST APIs.",
        level = CourseLevel.INTERMEDIATE,
        difficulty = 2,
        lessonCount = 9,
        estimatedHours = 5.0,
        iconName = "browser",
        progressPercent = 30,
        tags = listOf("WebDev", "HTML", "CSS", "Frontend"),
        modules = listOf(
          CourseModule(
            id = "mod_web1",
            title = "Frontend Foundations (HTML5 & CSS3)",
            chapters = listOf(
              Chapter(
                id = "chap_web1_1",
                title = "Semantic Markup",
                lessons = listOf(
                  Lesson("les_web1", "Semantic HTML5 Elements & Accessibility (a11y)", 20, "Header, nav, main, article, and ARIA attributes.", true),
                  Lesson("les_web2", "CSS Box Model, Selectors & Specificity", 22, "Margins, borders, padding, content box, and cascades.", true)
                )
              ),
              Chapter(
                id = "chap_web1_2",
                title = "Modern Layout Systems",
                lessons = listOf(
                  Lesson("les_web3", "Flexbox: Aligning & Distributing UI Elements", 25, "Justify content, align items, flex-grow, and wrapping.", false),
                  Lesson("les_web4", "CSS Grid: Complex Two-Dimensional Layouts", 28, "Grid template areas, auto-fit, minmax, and responsive grids.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_web2",
            title = "Responsive Web & Modern Architecture",
            chapters = listOf(
              Chapter(
                id = "chap_web2_1",
                title = "DOM & Mobile-First",
                lessons = listOf(
                  Lesson("les_web5", "Media Queries & Mobile-First Design Principles", 22, "Breakpoints for mobile, tablet, and desktop viewports.", false),
                  Lesson("les_web6", "DOM Manipulation & Event Listeners with JS", 28, "Dynamic element creation, class toggles, and event delegation.", false)
                )
              ),
              Chapter(
                id = "chap_web2_2",
                title = "Performance & Deployment",
                lessons = listOf(
                  Lesson("les_web7", "Web Performance, Minification & SEO Basics", 20, "Core Web Vitals, asset optimization, and meta tags.", false),
                  Lesson("les_web8", "Connecting to REST APIs from Webpages", 25, "Handling CORS, async fetch requests, and loading states.", false),
                  Lesson("les_web9", "Building & Deploying a Responsive Website", 35, "Deploying a live landing page to a production CDN.", false)
                )
              )
            )
          )
        )
      ),

      // 14. Database & SQL
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
      // ADVANCED COURSES (6 COURSES)
      // =================================================================

      // 15. App Development
      Course(
        id = "course_appdev",
        title = "App Development",
        description = "Build native Android apps with Kotlin and Jetpack Compose. State management, Room database, Coroutines, MVVM, and Play Store publishing.",
        level = CourseLevel.ADVANCED,
        difficulty = 3,
        lessonCount = 9,
        estimatedHours = 6.0,
        iconName = "smartphone",
        progressPercent = 15,
        tags = listOf("Android", "Kotlin", "Compose", "Mobile"),
        modules = listOf(
          CourseModule(
            id = "mod_app1",
            title = "Native Mobile Architecture",
            chapters = listOf(
              Chapter(
                id = "chap_app1_1",
                title = "Operating System & UI",
                lessons = listOf(
                  Lesson("les_app1", "Mobile Ecosystems: Android Architectural Layers", 25, "Linux kernel, HAL, ART runtime, and application framework.", true),
                  Lesson("les_app2", "Modern Declarative UI: Jetpack Compose Fundamentals", 30, "Composables, Recomposition, Modifiers, and Material 3 design.", false)
                )
              ),
              Chapter(
                id = "chap_app1_2",
                title = "State & Navigation",
                lessons = listOf(
                  Lesson("les_app3", "State Hoisting, ViewModels & Reactive Data Flow", 32, "StateFlow, SharedFlow, and lifecycle-aware collection.", false),
                  Lesson("les_app4", "Type-Safe Navigation & Screen Backstack", 28, "Setting up NavHost, routes, and passing serialized arguments.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_app2",
            title = "Hardware, Storage & Production",
            chapters = listOf(
              Chapter(
                id = "chap_app2_1",
                title = "Offline Persistence & Hardware",
                lessons = listOf(
                  Lesson("les_app5", "Local Persistence with Room Database & SQLite", 30, "Entities, DAOs, type converters, and migration strategies.", false),
                  Lesson("les_app6", "Sensors, Location, Permissions & Biometrics", 30, "Declaring and requesting runtime permissions gracefully.", false)
                )
              ),
              Chapter(
                id = "chap_app2_2",
                title = "Networking & Release",
                lessons = listOf(
                  Lesson("les_app7", "Asynchronous Networking with Retrofit & Coroutines", 30, "Background threads, Dispatchers.IO, and Moshi JSON parsing.", false),
                  Lesson("les_app8", "App Performance, Memory Profiling & LeakCanary", 28, "Detecting memory leaks, jank, and overdraw issues.", false),
                  Lesson("les_app9", "Publishing to the Google Play Store", 25, "Keystores, Android App Bundles (AAB), and release tracks.", false)
                )
              )
            )
          )
        )
      ),

      // 16. Linux
      Course(
        id = "course_linux",
        title = "Linux",
        description = "Master the Linux CLI, bash scripting, file system hierarchy, user permissions, process management, systemd, SSH, and server administration.",
        level = CourseLevel.ADVANCED,
        difficulty = 3,
        lessonCount = 8,
        estimatedHours = 5.0,
        iconName = "terminal",
        progressPercent = 40,
        tags = listOf("Linux", "CLI", "Bash", "SysAdmin"),
        modules = listOf(
          CourseModule(
            id = "mod_lin1",
            title = "The Linux File System & Shell",
            chapters = listOf(
              Chapter(
                id = "chap_lin1_1",
                title = "Terminal Foundations",
                lessons = listOf(
                  Lesson("les_lin1", "Linux File System Hierarchy (/etc, /var, /bin)", 22, "Understanding root directory structure and mount points.", true),
                  Lesson("les_lin2", "Essential Navigation & File Ops (ls, cd, cp, mv, rm)", 22, "Mastering command flags, globbing, and relative paths.", true)
                )
              ),
              Chapter(
                id = "chap_lin1_2",
                title = "Permissions & Streams",
                lessons = listOf(
                  Lesson("les_lin3", "Permissions: chmod, chown, SUID & Access Control", 28, "Octal notation (755, 644), group ownership, and umask.", false),
                  Lesson("les_lin4", "Stream Redirection, Pipes (|), Grep, Sed & Awk", 30, "Chaining standard input/output/error streams for data wrangling.", false)
                )
              )
            )
          ),
          CourseModule(
            id = "mod_lin2",
            title = "Administration & Automation",
            chapters = listOf(
              Chapter(
                id = "chap_lin2_1",
                title = "Processes & Services",
                lessons = listOf(
                  Lesson("les_lin5", "Process Control: ps, top, kill & Systemd Services", 28, "Managing background daemons with systemctl and journalctl.", false),
                  Lesson("les_lin6", "Package Management (apt, dnf, pacman) & Repositories", 22, "Dependency resolution, repository mirrors, and GPG keys.", false)
                )
              ),
              Chapter(
                id = "chap_lin2_2",
                title = "Scripting & Security",
                lessons = listOf(
                  Lesson("les_lin7", "Writing Bash Automation Scripts & Cron Jobs", 30, "Variables, loops, exit codes, and scheduled crontabs.", false),
                  Lesson("les_lin8", "Hardening Linux SSH Servers & Firewall Rules", 28, "Key-based authentication, ufw/iptables, and fail2ban setup.", false)
                )
              )
            )
          )
        )
      ),

      // 17. Cybersecurity Fundamentals
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

      // 18. Cloud Computing
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

      // 19. Advanced IT
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
      ),

      // 20. Artificial Intelligence
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
      )
    )
  }
}
