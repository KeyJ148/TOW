package engine.implementation;

public interface StorageInterface {

    //Engine: чем больше глубина, тем больше объектов будет отрисовыватсья поверх
    //Engine: объекты отрисовываются в порядке уменьшения глубины

    default String getPathImagesRoot(){ return "res/image/"; } //Engine: Путь к корню папки с текстурами
    String[][] getImages();  //Engine: Описание картинки (Путь от корня, тип объекта, глубина)

    default String getPathAnimationsRoot(){ return "res/animation/"; }//Engine: Путь к корню папки с анимацией
    String[][] getAnimations();//Описание анимации (Путь от корня, тип объекта, глубина)

    int[][] getFonts(); //Engine: Описание шрифта (Размер, тип)
    String[] getAudios(); //Engine: Путь к файлу звуков (.wav)

}
