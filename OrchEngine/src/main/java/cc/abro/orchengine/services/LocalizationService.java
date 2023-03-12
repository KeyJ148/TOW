package cc.abro.orchengine.services;

import cc.abro.orchengine.context.EngineService;

import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.*;

/**
 * Сервис локализации. Пока что не умеет в загрузку локалей из файла, но переводики можно вписать в DEFAULT_LOCALE.<br>
 * Чекай закономерность для {@link LocalizationService#DEFAULT_LOCALE}:<br><br>
 * <code>
 * localize("locale.id") -> ru <br>
 * localize("sample.text") -> SAMPLE_TEXT <br>
 * loc.localize("sample.format") -> SAMPLE{0}TEXT <br>
 * loc.localize("sample.format", "*") -> SAMPLE*TEXT <br>
 * loc.localize("explicit.error") -> [[explicit.error]]
 * </code><br>
 * <b style="color: red;">Потом допилю, не плачь</b>
 */
@EngineService
public class LocalizationService {
	/**
	 * Временная захардкоженная локализация в формате properties.
	 * Наличие значения locale.id <b>ОБЯЗАТЕЛЬНО</b>
	 */
	private static final String DEFAULT_LOCALE = """
locale.id = ru
sample.text = SAMPLE_TEXT
sample.format = SAMPLE{0}TEXT
			""";
	private ArrayList<Localization> locales = new ArrayList<>();
	private Localization currentLocale = Localization.loadFromProps(DEFAULT_LOCALE); // Костыль.

	// вызови меня полностью.
	public LocalizationService(){
		locales.add(currentLocale); // продолжение костыля.
		// Где-то тут должны сканироваться и собираться локали. Потом прикрутим, главное шоб работало.
	}

	/**
	 * @return Список известных id локалей
	 */
	public ArrayList<String> getlocales(){
		ArrayList<String> localeIds = new ArrayList<>();
		for(var locale : locales){
			localeIds.add(locale.id);
		}
		return localeIds;
	}

	/**
	 * Сменяет используемую локаль
	 * @param id id искомой локали
	 * @return true, если локаль смененаи false, если нет
	 */
	public boolean changeLocale(String id){
		try{
			currentLocale = locales.stream().filter(l -> Objects.equals(l.id, id)).findFirst().get();
			return true;
		} catch (Exception e){
			return false;
		}
	}

	/**
	 * Возвращает локализованную или псевдолокализованную строку, соответствующую указанному ключу.
	 * @param key ключ.
	 * @return Переведённый текст.
	 */
	public String localize(String key){
		return currentLocale.localize(key);
	}

	/**
	 * Пытается локализовать и форматировать строку.
	 * @param key ключ.
	 * @param args объекты для String.format
	 * @return Переведённый и отформатированный текст
	 */
	public String localize(String key, Object... args){
		return currentLocale.localize(key, args);
	}







	/**
	 * Класс локализации. В теории должен уметь в весь i18n, но давай не надо.
	 */
	// TODO: А че, обязательно весь сервис писать одним классом/файлом? Куда это всё выносить если нет?
	private static class Localization{
		private Map<String, String> localeMap = new HashMap<>();
		private String id;

		/**
		 * Смотри {@link LocalizationService#localize(String) здесь}
		 */
		public String localize(String key){
			String localizedString = localeMap.get(key);
			if(localizedString == null) return pseudolocalize(key);
			return localizedString;
		}

		/**
		 * Смотри {@link LocalizationService#localize(String, Object...) здесь}
		 */
		public String localize(String key, Object... args){
			String localizedString = localeMap.get(key);
			if(localizedString == null) return pseudolocalize(key);

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
				// TODO: потом обработаю (нет)
			}

			for(String key : props.stringPropertyNames()) {
				locale.localeMap.put(key, props.getProperty(key));
			}
			locale.id = locale.localize("locale.id"); // что-то на хардкодном
			if(locale.id.equals(Localization.pseudolocalize("locale.id"))){
				return null;
			}
			return locale;
		}
	}


}

