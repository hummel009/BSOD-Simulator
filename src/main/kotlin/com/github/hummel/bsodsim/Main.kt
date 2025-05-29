package com.github.hummel.bsodsim

import java.awt.*
import javax.swing.JFrame
import javax.swing.JTextArea
import javax.swing.text.Highlighter
import kotlin.math.round

fun main() {
	EventQueue.invokeLater {
		try {
			val frame = BSOD()
			frame.isVisible = true
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}

class BSOD : JFrame() {
	init {
		val screenSize = Toolkit.getDefaultToolkit().screenSize
		val screenWidth = screenSize.width

		defaultCloseOperation = DO_NOTHING_ON_CLOSE
		isResizable = false
		isUndecorated = true
		isAlwaysOnTop = true
		size = screenSize

		val textArea = object : JTextArea(win7String) {
			override fun getHighlighter(): Highlighter? = null
			override fun getCaretColor(): Color = Color(0, 0, 130)
		}

		val fontSize = round(screenWidth.toDouble() * 39.0 / 1920.0).toFloat()
		var font = Font.createFont(
			Font.TRUETYPE_FONT, BSOD::class.java.getResourceAsStream(
				"/lucidaconsole.ttf"
			)
		)
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font)
		font = font.deriveFont(fontSize)

		textArea.font = font
		textArea.foreground = Color.WHITE
		textArea.background = Color(0, 0, 130)
		textArea.lineWrap = true
		textArea.wrapStyleWord = true
		textArea.isEditable = false

		val margin = round(screenWidth.toDouble() / 100.0).toInt()
		textArea.margin = Insets(margin, margin, margin, margin)
		textArea.setBounds(0, 0, width, height)

		val transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(
			Toolkit.getDefaultToolkit().createImage(""), Point(0, 0), "invisibleCursor"
		)
		setCursor(transparentCursor)

		add(textArea)
		setLocationRelativeTo(null)
	}
}

var win7String: String = """
	A problem has been detected and Windows has been shut down to prevent damage to your computer.
	
	PROCESS_INITIALIZATION_FAILED
	
	If this is the first time you've seen this Stop error screen, restart your computer. If this screen appears again, follow these steps:
	
	Check to make sure any new hardware or software is properly installed. If this is a new installation, ask your hardware or software manufacturer for any Windows updates you might need.
	
	If problems continue, disable or remove any newly installed harware or software. Disable BIOS memory options such as caching or shadowing. If you need to use Safe Mode to remove or disable components, restart your computer, press F8 to select Advanced Startup Options, and then select Safe Mode.
	
	Technical information:
	
	*** STOP: 0x00000060 (0xF2N094c2,0x00000001,0x4FQ1ccc7,0x0000000)
	
	***       4FQ.sys - Address FWTV1999 base at 4s4M5000, Datestamp 4d5dd88c
	
	Beginning dump of physical memory
	Physical memory dump complete
	Contact your system administrator or technical support for further assistance.
	""".trimIndent()
