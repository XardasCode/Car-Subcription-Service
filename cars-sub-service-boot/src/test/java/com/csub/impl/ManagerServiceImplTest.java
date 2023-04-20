package com.csub.impl;

import com.csub.dao.ManagerDAO;
import com.csub.dto.ManagerDTO;
import com.csub.dto.mapper.ManagerDTOMapper;
import com.csub.entity.Subscription;
import com.csub.entity.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;


@ExtendWith(MockitoExtension.class)
class ManagerServiceImplTest {
    @InjectMocks
    private ManagerServiceImpl managerService;
    @Mock
    private ManagerDAO managerDAO;
    private  ManagerDTOMapper managerDTOMapper = new ManagerDTOMapper();
    Manager manager;

    @BeforeEach
    void setUp()
    {
        managerService = new ManagerServiceImpl(managerDAO,managerDTOMapper);
        Subscription subscription = Subscription.builder().build();
        Set<Subscription> subscriptionSet = new HashSet<>();
        subscriptionSet.add(subscription);
        manager = Manager.builder()
                .id(1)
                .name("John")
                .surname("Wick")
                .email("killer2077@gmail.com")
                .password("TheLordOfDarkness")
                .subscriptions(subscriptionSet)
                .build();
    }
    @DisplayName("addManager checks if the managerDAO method is called")
    @Test
    void addManager() {
        Mockito.doNothing().when(managerDAO).addManager(ArgumentMatchers.any());
        managerService.addManager(manager);
        Mockito.verify(managerDAO, Mockito.times(1)).addManager(manager);
    }
    @DisplayName("getManager must return valid manager when id is valid")
    @Test
    void getManager() {
        Mockito.when(managerDAO.getManager(manager.getId())).thenReturn(Optional.of(manager));
        ManagerDTO actual = managerService.getManager(manager.getId());
        Mockito.verify(managerDAO, Mockito.times(1)).getManager(manager.getId());
        assertAll(()->{
            assertEquals(manager.getId(),actual.id());
            assertEquals(manager.getName(),actual.name());
        });
    }
    @DisplayName("getManager must throw ServerException when id is not valid")
    @Test
    void getManagerMustThrowServerException() {
        Manager nullManager = new Manager();
        Mockito.when(managerDAO.getManager(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> managerService.getManager(manager.getId()))
                .hasMessage("Manager not found");
        Mockito.verify(managerDAO, Mockito.times(1)).getManager(manager.getId());
    }
    @DisplayName("addManager checks if the managerDAO method is called")
    @Test
    void updateManager() {
        Mockito.doNothing().when(managerDAO).updateManager(ArgumentMatchers.any());
        Mockito.when(managerDAO.getManager(manager.getId())).thenReturn(Optional.of(manager));
        managerService.updateManager(manager);
        Mockito.verify(managerDAO, Mockito.times(1)).updateManager(manager);
    }
    @DisplayName("addManager checks if the managerDAO method is called")
    @Test
    void deleteManager() {
        Mockito.doNothing().when(managerDAO).deleteManager(anyLong());
        Mockito.when(managerDAO.getManager(manager.getId())).thenReturn(Optional.of(manager));
        managerService.deleteManager(manager.getId());
        Mockito.verify(managerDAO, Mockito.times(1)).deleteManager(manager.getId());
    }
    @DisplayName("getManagers must return list of managers")
    @Test
    void getManagers() {
        List<Manager> list = new ArrayList<>();
        list.add(manager);
        Mockito.when(managerDAO.getAllManagers()).thenReturn(list);
        List<ManagerDTO> actual = managerService.getAllManagers();
        Mockito.verify(managerDAO, Mockito.times(1)).getAllManagers();
        assertAll(()->{
            assertEquals(manager.getId(),actual.get(0).id());
            assertEquals(manager.getName(),actual.get(0).name());
        });
    }
}