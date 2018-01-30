package event.project.controllers;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import events.project.controllers.EventController;
import events.project.exceptions.EventExistException;
import events.project.modelDto.EventDto;
import events.project.modelEntity.Address;
import events.project.modelEntity.Point;
import events.project.modelEntity.User;
import events.project.services.EventServiceImpl;
import events.project.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
@RunWith(SpringRunner.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventServiceImpl eventService;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new EventController(eventService,userService)).build();
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    Point mockPoint = new Point(15.4785f, 58.1458f);
    Address mockAddress = new Address("WARSZAWA", "Kwiatowa", "9");
    LocalDateTime mockBeggining = LocalDateTime.of(2018, Month.JANUARY, 15, 15, 00);
    LocalDateTime mockEnding = LocalDateTime.of(2018, Month.JANUARY, 31, 20, 00);


    EventDto mockEventDto = new EventDto("Alvaro Soler", "KONCERT",
            mockPoint, mockAddress, mockBeggining, mockEnding, 1l, false);
    EventDto mockEventDto2 = new EventDto("Festiwal wina", "IMPREZA",
            mockPoint, mockAddress, mockBeggining, mockEnding, 1l, false);

    User user = new User("Agnieszka", "Puzia", "agnieszka.puzia@gmail.com", "123456");


    @Test
    @WithMockUser(username = "user1", password = "password1", roles = "USER")
    public void getAllEvents() throws Exception {
        List<EventDto> events = Arrays.asList(mockEventDto,mockEventDto2);

        when(eventService.findAll()).thenReturn(events);
        mockMvc.perform(get("/allEvents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$[0].name").value("Alvaro Soler"))
                .andExpect((ResultMatcher) jsonPath("$[0].eventType").value("KONCERT"))
                .andExpect((ResultMatcher) jsonPath("$[1].name").value("Festiwal wina"))
                .andExpect((ResultMatcher) jsonPath("$[1].eventType").value("IMPREZA"));
        verify(eventService, times(1)).findAll();
        verifyNoMoreInteractions(eventService);
    }


    @Test
    @WithMockUser(username = "user1", password = "password1", roles = "USER")
    public void getEventById() throws Exception {
        EventDto event = mockEventDto;
        when(eventService.findById(1l)).thenReturn(event);
        mockMvc.perform(get("/event/{id}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name").value("Alvaro Soler"))
                .andExpect(jsonPath("$.endingDateTime").value((LocalDateTime.of(2018, Month.JANUARY, 31, 20, 00)).format(formatter)));
        verify(eventService, times(1)).findById(1l);
        verifyNoMoreInteractions(eventService);
    }

    @Test
    @WithMockUser(username = "user1", password = "password1", roles = "USER")
    public void addEvent() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        EventDto event = mockEventDto;
        EventDto eventReturn = mockEventDto;
        //when(eventService.isEventExist(event)).thenReturn(false);
        when(eventService.saveEvent(user,event)).thenReturn(eventReturn);

        mockMvc.perform(post("/addEvent")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value("Alvaro Soler"))
                .andExpect(jsonPath("$.endingDateTime").value((LocalDateTime.of(2018, Month.JANUARY, 31, 20, 00)).format(formatter)));

        ArgumentCaptor<EventDto> dtoCaptor = ArgumentCaptor.forClass(EventDto.class);
        verify(eventService, times(1)).saveEvent(user,event);
        verifyNoMoreInteractions(eventService);

    }



    @Test
    @WithMockUser(username = "user1", password = "password1", roles = "USER")
    public void addEventIsConfilct() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        EventDto event = mockEventDto;

        when(eventService.saveEvent(user,event)).thenThrow(new EventExistException(event));
        mockMvc.perform(post("/addEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(event)))
                .andExpect(status().isConflict());
        verify(eventService, times(1)).isEventExist(event);
        verifyNoMoreInteractions(eventService);


    }








}

