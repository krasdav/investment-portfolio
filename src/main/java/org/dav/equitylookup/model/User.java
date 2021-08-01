package org.dav.equitylookup.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name  = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String role = "None";
    private String password = "password";
    private BigDecimal portfolio = new BigDecimal("0");

    @OneToMany
    private List<UserStockInfo> userStocksInfo = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "stock_owned",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name ="stock_id")
    )
    private List<Stock> stocks = new ArrayList<>();

    public User(){}

    public User(String username){
        this.username = username;
    }

    public User(Long id, String username, BigDecimal portfolio) {
        this.id = id;
        this.username = username;
        this.portfolio = portfolio;
    }

    public void addStock(Stock stock, UserStockInfo userStockInfo){
        userStocksInfo.add(userStockInfo);
        stocks.add(stock);
    }

    public void addStocks(List<Stock> stocks){
        this.stocks.addAll(stocks);
    }

    public void removeStock(Stock stock){
        stocks.remove(stock);
    }

    public void addToPortfolio(BigDecimal stockValue){
        portfolio = portfolio.add(stockValue);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
