package events.project.controllers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

import events.project.modelDto.EventDto;
import events.project.modelEntity.Address;
import events.project.modelEntity.Event;
import events.project.modelEntity.Point;
import events.project.modelEntity.User;
import events.project.services.EventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@WebMvcTest(value = EventController.class, secure = false)
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

    String exampleEventJson =
    "{" +
       " \"name\": \"Alvalo Soler\"," +
          "  \"eventType\": \"MUZYKA\"," +
           " \"point\": {"+
            " \"longitude\": 27.4513," +
               " \"latitude\": 55.1255," +
                       " \"draggable\": false" +
                       " }," +
                       " \"address\": {" +
                       "\"street\": \"Lesna\"," +
                       " \"city\": \"WARSZAWA\"," +
                       " \"number\": \"11\"" +
                       " }," +
                       "\"beginningDateTime\": \"15-02-2018 21:30\"," +
                       " \"endingDateTime\": \"15-02-2018 23:00\"," +
                       " \"userId\": 1," +
                       " \"confirm\": false" +
                       " }" ;


    @Test
    public void findByIdForCourse() throws Exception {

        Mockito.when(eventService.findById(Mockito.anyLong())).thenReturn(mockEventDto);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/event/1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{name:Alvalo Soler,eventType:MUZYKA}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

}

