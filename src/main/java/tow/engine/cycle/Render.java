package tow.engine.cycle;

import legui.ExampleGui;
import legui.MenuLayerDemo;
import org.liquidengine.legui.DefaultInitializer;
import org.liquidengine.legui.animation.Animator;
import org.liquidengine.legui.animation.AnimatorProvider;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.listener.CursorEnterEventListener;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.WindowSizeEventListener;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.layout.LayoutManager;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import tow.engine.Global;
import tow.engine.logger.Logger;
import tow.engine.Loader;
import tow.engine.resources.settings.SettingsStorage;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Render{

	private long windowID; //ID окна игры для LWJGL
	private long monitorID; //ID монитора (0 для не полноэкранного режима)
	private int width;
	private int height;

	public void initGL(){
		//Инициализация GLFW
		if (!glfwInit()){
			Global.logger.println("GLFW initialization failed", Logger.Type.ERROR);
			Loader.exit();
		}

		//Инициализация и настройка окна
		try {
			//Установка параметров для окна
			glfwDefaultWindowHints();
			glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
			glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

			//Получение разрешения экрана
			GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			int monitorWidth = vidMode.width();
			int monitorHeight = vidMode.height();

			//Выбор экрана и размеров окна
			if (SettingsStorage.DISPLAY.FULL_SCREEN){
				monitorID = glfwGetPrimaryMonitor();
				width = monitorWidth;
				height = monitorHeight;
			} else {
				monitorID = 0;
				width = SettingsStorage.DISPLAY.WIDTH_SCREEN;
				height = SettingsStorage.DISPLAY.HEIGHT_SCREEN;
			}

			//Создание окна
			windowID = glfwCreateWindow(width, height, SettingsStorage.DISPLAY.WINDOW_NAME, monitorID, NULL);
			//Перемещение окна на центр монитора
			glfwSetWindowPos(windowID, (monitorWidth - width)/2, (monitorHeight - height)/2);
			//Создание контекста GLFW
			glfwMakeContextCurrent(windowID);
			//Включение VSync: будет происходить синхронизация через каждые N кадров
			glfwSwapInterval(SettingsStorage.DISPLAY.VSYNC_DIVIDER);
			//Создание контекста OpenGL
			GL.createCapabilities();

			//Настройка графики
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_COLOR_MATERIAL);
			GL11.glDisable(GL11.GL_DEPTH_TEST);

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, width, height, 0, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			//Включение видимости окна
			glfwShowWindow(windowID);
			//Заливка всего фона черным цветом
			GL11.glClearColor(0f, 0f, 0f, 0f);
			//Обновление кадра
			glfwSwapBuffers(windowID);
		} catch (Exception e) {
			Global.logger.println("OpenGL initialization failed", e, Logger.Type.ERROR);
			Loader.exit();
		}

		initGUI();
	}

	public void loop() {
		prerender();
		Global.game.render(); //Отрисовка в главном игровом классе (ссылка передается в движок при инициализации)
		Global.room.render(getWidth(), getHeight()); //Отрисовка комнаты
		renderGUI();
		normilizeGL();
		Global.mouse.draw(); //Отрисовка мыши
	}

	DefaultInitializer initializer;
	public Frame frame;
	Animator animator;

	public void initGUI(){
		frame = new Frame(400, 200);
		createFrameWithGUI(frame);

		initializer = new DefaultInitializer(Global.engine.render.getWindowID(), frame);
		//Global.mouse.getEventHistory().init(initializer);
		//initializer.getCallbackKeeper().getChainMouseButtonCallback().add();

		animator = AnimatorProvider.getAnimator();
		//Инициализация callbacks
		initializer.getRenderer().initialize();
	}

	public void prerender(){
		initializer.getContext().updateGlfwWindow();
		glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

		//normilizeGL();
	}

	public void normilizeGL(){
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void renderGUI(){
		Context context = initializer.getContext();

		/*
		int[] windowWidth = {0}, windowHeight = {0};
		GLFW.glfwGetWindowSize(Global.engine.render.getWindowID(), windowWidth, windowHeight);
		int[] frameBufferWidth = {0}, frameBufferHeight = {0};
		GLFW.glfwGetFramebufferSize(Global.engine.render.getWindowID(), frameBufferWidth, frameBufferHeight);
		int[] xpos = {0}, ypos = {0};
		GLFW.glfwGetWindowPos(Global.engine.render.getWindowID(), xpos, ypos);
		double[] mx = {0}, my = {0};
		GLFW.glfwGetCursorPos(Global.engine.render.getWindowID(), mx, my);


		context.update(windowWidth[0], windowHeight[0],
		        frameBufferWidth[0], frameBufferHeight[0],
		        xpos[0], ypos[0],
		        false //!!!!!!!!! In example 8 paametrs
		);
		*/



		initializer.getRenderer().render(frame, context);
/*
		glClear(GL_COLOR_BUFFER_BIT); //ИЗ РЕНДЕР!!!!!!!

		Vector2i windowSize = context.getFramebufferSize();
		glViewport(0, 0, windowSize.x, windowSize.y);
		glClearColor(1, 1, 1, 1);
		glViewport(0, 0, windowSize.x, windowSize.y);
		glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
*/

		//Настройка графики
		//GL11.glEnable(GL11.GL_TEXTURE_2D);
		//GL11.glDisable(GL11.GL_COLOR_MATERIAL);
		///////GL11.glDisable(GL11.GL_DEPTH_TEST);

		///////GL11.glEnable(GL11.GL_BLEND);
		///////GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		//GL11.glMatrixMode(GL11.GL_PROJECTION);
		//GL11.glLoadIdentity();
		//GL11.glOrtho(0, Global.engine.render.getHeight(), Global.engine.render.getHeight(), 0, 1, -1);
		//GL11.glMatrixMode(GL11.GL_MODELVIEW);

		//SwapBuffers & PollEvents


		// Now we need to handle events. Firstly we need to handle system events.
		// And we need to know to which frame they should be passed.
		initializer.getSystemEventProcessor().processEvents(frame, context);
		// When system events are translated to GUI events we need to handle them.
		// This event processor calls listeners added to ui components
		initializer.getGuiEventProcessor().processEvents();

		// When everything done we need to relayout components.
		LayoutManager.getInstance().layout(frame);

		// Run animations. Should be also called cause some components use animations for updating state.
		animator.runAnimations();
	}

	private Frame createFrameWithGUI(Frame frame) {
		// Set background color for frame
		frame.getContainer().getStyle().getBackground().setColor(ColorConstants.transparent());
		frame.getContainer().setFocusable(true);

		Button button = new Button("Add components", 20, 20, 160, 30);
		SimpleLineBorder border = new SimpleLineBorder(ColorConstants.black(), 1);
		button.getStyle().setBorder(border);

		boolean[] added = {false};
		button.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
			if (!added[0]) {
				added[0] = true;
				for (Component c : generateOnFly()) {
					frame.getContainer().add(c);
				}
			}

		});

		button.getListenerMap().addListener(CursorEnterEvent.class, (CursorEnterEventListener) System.out::println);

		frame.getContainer().add(button);
		return frame;
	}

	private List<Component> generateOnFly() {
		List<Component> list = new ArrayList<>();

		Label label = new Label(20, 60, 200, 20);
		label.getTextState().setText("Generated on fly label");

		RadioButtonGroup group = new RadioButtonGroup();
		RadioButton radioButtonFirst = new RadioButton("First", 20, 90, 200, 20);
		RadioButton radioButtonSecond = new RadioButton("Second", 20, 110, 200, 20);

		radioButtonFirst.setRadioButtonGroup(group);
		radioButtonSecond.setRadioButtonGroup(group);

		list.add(label);
		list.add(radioButtonFirst);
		list.add(radioButtonSecond);

		return list;
	}


	public void vsync(){
		glfwSwapBuffers(windowID);
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public long getWindowID() {
		return windowID;
	}

	public long getMonitorID() {
		return monitorID;
	}
}
