# 📚 Book Manager Pro - Java Swing Application

> **Hinweis:** Dieses Projekt wurde ursprünglich im **[Oktober 2025]** entwickelt und nun für Portfolio-Zwecke auf GitHub veröffentlicht.

**Book Manager Pro** ist eine robuste Desktop-Anwendung zur Verwaltung von Bibliotheksbeständen, entwickelt mit **Java** und **Swing**. Diese Anwendung demonstriert professionelle Softwarearchitektur, sauberen Code und moderne Benutzeroberflächen-Entwicklung ohne externe Abhängigkeiten.

<img width="830" height="558" alt="Library-Pro" src="https://github.com/user-attachments/assets/8aae29c3-e524-4d5f-958f-de041979a909" />


## 🚀 Hauptfunktionen (Key Features)

* **🏗️ MVC-Architektur:** Klare Trennung zwischen Daten (Model), Logik (Service) und Benutzeroberfläche (View).
* **💾 Datenpersistenz:** Automatische Speicherung und Laden von Büchern über CSV-Dateien (keine Datenbank erforderlich).
* **🌍 Mehrsprachigkeit:** Vollständige Unterstützung für **Deutsch**, **Englisch** und **Persisch (Farsi)** mit Sofort-Umschaltung.
* **🎨 Dark & Light Mode:** Professionelles Theme-Management für ergonomisches Arbeiten bei allen Lichtverhältnissen.
* **🔍 Live-Suche:** Echtzeit-Filterung der Bücherliste nach Titel oder Autor.
* **⚡ CRUD-Operationen:** Hinzufügen, Bearbeiten, Löschen und Statusänderung (Ausleihen/Rückgabe) von Büchern.
* **🛡️ Validierungslogik:** Schutz vor unvollständigen Eingaben und ungültigen Ausleihen.

## 🛠️ Technologie-Stack

* **Sprache:** Java (JDK 8+)
* **GUI-Framework:** Java Swing & AWT
* **Design-Pattern:** OOP (Objektorientierte Programmierung), MVC-Ansatz
* **Datenhaltung:** File I/O (CSV)
* 

## 🧩 Architektur-Highlights

Der Code ist nach Clean-Code-Prinzipien strukturiert:

* **`Book` Class:** Repräsentiert das Datenmodell (POJO).
* **`DataService` Class:** Handhabt das Lesen und Schreiben der CSV-Datei.
* **`ThemeManager` Class:** Verwaltet rekursiv die Farben aller UI-Komponenten für den Dark Mode.
* **`LangManager` Class:** Zentralisiert alle Texte für die einfache Lokalisierung.
* **`BookManagerPro` Class:** Die Hauptklasse, die GUI und Logik verbindet.
