package org.dav.equitylookup.controller;

import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.implementation.StockSearchService;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private StockSearchService stockSearchService;

    @Mock
    private StockService stockService;

    @Mock
    private ModelMapper modelMapper;

    private AdminController adminController;

    private UserController userController;

    private User userOne;
    private User userTwo;
    private Stock stockINTC;
    private Stock stockGOOG;

//    @BeforeEach
//    void initUseCase() {
//        adminController = new AdminController(userService,modelMapper);
//        stockINTC = new Stock(new BigDecimal("500"));
//        stockGOOG = new Stock(new BigDecimal("400"));
//        userOne = new User("David");
//        userTwo = new User("Sara");
//
//        userOne.addStocks(List.of(stockINTC,stockGOOG));
//    }
//
//    @Test
//    @DisplayName("View all users - Verify Model attributes")
//    void viewUsers() {
//        List<User> userList = List.of(userOne,userTwo);
//        Model model = new ExtendedModelMap();
//        when(userService.getAllUsers()).thenReturn(userList);
//
//        adminController.viewUsers(model);
//
//        assertThat(model.getAttribute("users"), is(userList));
//    }
//
//    @Test
//    @DisplayName("List stocks for user - Verify portfolio calculation and Model attributes")
//    void listStocksPortfolio() throws IOException {
//        Model model = new ExtendedModelMap();
//        when(userService.getUserByUsername(any())).thenReturn(userOne);
//        when(modelMapper.map(any(),any())).thenReturn(userOne);
//        when(stockSearchService.findPrice(any()))
//                .thenReturn(new BigDecimal("500"), new BigDecimal("400"));
//        adminController.listStocks(new UserDTO(userOne), null, model);
//        assertThat(model.getAttribute("stocks"), is(List.of(stockINTC,stockGOOG)));
//        assertThat(model.getAttribute("portfolioValue"), is(new BigDecimal("900")));
//    }

}