package demo.config;

import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Import(SpringConfig.class)
public class MvcConfig {}