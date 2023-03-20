package cc.abro.orchengine.resources.locales;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Класс локализации. В теории должен уметь в весь i18n, но давай не надо. <br>
 * Локализация должна содержать поля id и name
 */
@Log4j2
public class Localization{
	private final Map<String, String> localeMap = new HashMap<>();
	@Getter
	private String id;
	@Getter
	private String name;

	/**
	 * Смотри {@link LocalizationService#localize(String) здесь}
	 */
	public String localize(String key){
		return localeMap.getOrDefault(key, pseudolocalize(key));
	}

	/**
	 * Смотри {@link LocalizationService#localize(String, Object...) здесь}
	 */
	public String localize(String key, Object... args){
		String localizedString = localeMap.get(key);
		if(localizedString == null) return pseudolocalize(key); // Проверка на null нужна, чтобы убедиться, что дальнейшее форматирование имеет смыслщ

		localizedString = MessageFormat.format(localizedString, args);
		return localizedString;
	}

	// Добавляет к строке "декорации", указывающие на то, что она не соответствует ни одному ключу в словаре локализации
	private static String pseudolocalize(String s) {
		return "[[" + s + "]]";
	}

	/**
	 * Загружает словарь из properties-кода. Код обязательно должен содержать locale.id , типа ru, en-US, и чет такое
	 * @param code код properties
	 * @return Локализацию, либо null, если что-то пошло не так. А тут всё пойдет не так. Чекай null короче
	 */
	public static Localization loadFromProps(String code){
		var locale = new Localization();

		Properties props = new Properties();
		try {
			props.load(new StringReader(code));
		} catch (IOException e) {
			log.warn("Unable to load locale from props code:\n"+code); // окей, памяти у нас много
			// TODO: потом обработаю (нет)
		}

		for(String key : props.stringPropertyNames()) {
			locale.localeMap.put(key, props.getProperty(key));
		}
		locale.id = locale.localize("locale.id"); // что-то на хардкодном
		locale.name = locale.localize("locale.name"); // что-то на хардкодном
		if(locale.id.equals(Localization.pseudolocalize("locale.id"))){
			return null;
		}
		if(locale.name.equals(Localization.pseudolocalize("locale.name"))){
			log.warn("Unable to find locale name for '"+locale.id+"'");
			locale.name = locale.id;
		}
		log.info("Loaded locale: "+locale.id);

		return locale;
	}
}
