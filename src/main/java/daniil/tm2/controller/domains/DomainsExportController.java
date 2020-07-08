package daniil.tm2.controller.domains;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import daniil.tm2.api.service.IDomainService;
import daniil.tm2.constant.DataConstant;
import daniil.tm2.entity.Domain;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/domainsExport", method = RequestMethod.POST)
public class DomainsExportController {
    
    @Autowired
    private IDomainService domainService;

    @GetMapping
    @Secured("ROLE_NORMAL_USER")
    public String export(HttpServletRequest request) {
        String domainName = request.getParameter("domain");
        Domain domain = domainService.findByName(domainName);
        writeDomain(domain);
        return "redirect:/domains?exportok=true";
    }

    private void writeDomain(Domain domain) {
        try {
            final ObjectMapper objectMapper = new XmlMapper();
            final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
            final String json = objectWriter.writeValueAsString(domain);
            final byte[] data = json.getBytes("UTF-8");
            final File file = new File(DataConstant.FILE_XML);
            Files.write(file.toPath(), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
