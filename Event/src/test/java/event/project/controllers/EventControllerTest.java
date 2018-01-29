package event.project.controllers;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import events.project.EventApplication;
import events.project.WebConfiguration;
import events.project.controllers.EventController;
import events.project.modelDto.EventDto;
import events.project.modelEntity.Address;
import events.project.modelEntity.Point;
import events.project.services.EventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
@Configuration
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;


    Point mockPoint = new Point(15.4785f, 58.1458f);
    Address mockAddress = new Address("WARSZAWA", "Kwiatowa", "9");
    LocalDateTime mockBeggining = LocalDateTime.of(2018, Month.JANUARY, 15, 15, 00);
    LocalDateTime mockEnding = LocalDateTime.of(2018, Month.JANUARY, 31, 20, 00);

    EventDto mockEventDto = new EventDto("Alvaro Soler", "KONCERT",
            mockPoint, mockAddress, mockBeggining, mockEnding, 1l, false);
    EventDto mockEventDto2 = new EventDto("Festiwal wina", "IMPREZA",
            mockPoint, mockAddress, mockBeggining, mockEnding, 1l, false);



    @Test
    public void getAllEvents() throws Exception {
        List<EventDto> events = Arrays.asList(mockEventDto,mockEventDto2);

        when(eventService.findAll()).thenReturn(events);
        mockMvc.perform(get("/allEvents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$[0].name", is("Alvaro Soler")))
                .andExpect((ResultMatcher) jsonPath("$[0].eventType", is("KONCERT")))
                .andExpect((ResultMatcher) jsonPath("$[1].name", is("Festiwal wina")))
                .andExpect((ResultMatcher) jsonPath("$[1].eventType", is("IMPREZA")));
        verify(eventService, times(1)).findAll();
        verifyNoMoreInteractions(eventService);
    }


    @Test
    public void getEventById() throws Exception {
        EventDto event = mockEventDto;
        when(eventService.findById(1l)).thenReturn(event);
        mockMvc.perform(get("/event/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect((ResultMatcher) jsonPath("$.name", is("Alvaro Soler")))
                .andExpect((ResultMatcher) jsonPath("$.endingDateTime", is(mockEnding)));
        verify(eventService, times(1)).findById(1l);
        verifyNoMoreInteractions(eventService);
    }


}

