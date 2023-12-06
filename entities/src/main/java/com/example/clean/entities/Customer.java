package com.example.clean.entities;

public class Customer {

    private String id;
    private String email;
    private String lastName;
    private String firstName;

    // required for mvc servlet json deserialization
    Customer() {

    }

    private Customer(final String id, final String email, final String lastName, final String firstName) {
        this.id = id;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }

    public static class CustomerBuilder {
        private String id;
        private String email;
        private String lastName;
        private String firstName;

        CustomerBuilder() {
        }

        public CustomerBuilder id(final String id) {
            this.id = id;
            return this;
        }

        public CustomerBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public CustomerBuilder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CustomerBuilder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Customer build() {
            return new Customer(id, email, lastName, firstName);
        }
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id='" + id + '\'' +
            ", email='" + email + '\'' +
            ", lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            '}';
    }
}
