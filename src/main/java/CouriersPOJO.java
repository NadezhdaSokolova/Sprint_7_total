public class CouriersPOJO {

    // поля соответствуют ключам json

    private String login;
    private String password;

    private String firstName;

    // добавляем конструкторы: для инициализации полей + пустой для библиотеки

    public CouriersPOJO(String login, String password, String firstName){
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CouriersPOJO(){
    }

    // устанавливаем гетерры и сеттеры

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



}
