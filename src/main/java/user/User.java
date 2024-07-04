package user;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    public static User createDefaultUser(){
        User user = new User();
        user.setId(11);
        user.setName("test");
        user.setUsername("user");
        user.setEmail("asdasd@gmail.com");
        user.setAddress(createDefaultAddress());
        user.setPhone("+380-------");
        user.setWebsite("google.com");
        user.setCompany(createDefaultCompany());
        return user;
    }

    private static Address createDefaultAddress(){
        Address address = new Address();
        address.setStreet("123");
        address.setSuite("456");
        address.setCity("Kiev");
        address.setZipcode("123-321");
        address.setGeo(createDefaultGeo());

        return address;
    }

    private static Geo createDefaultGeo(){
        Geo geo = new Geo();
        geo.setLat("1241");
        geo.setLng("1421");
        return geo;
    }
    private static Company createDefaultCompany(){
        Company company = new Company();
        company.setName("Romaguera-Crona");
        company.setBs("harness real-time e-markets");
        company.setCatchPhrase("Multi-layered client-server neural-net");
        return company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!Objects.equals(name, user.name)) return false;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", company=" + company +
                '}';
    }
}

