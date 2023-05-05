package com.eun.tutorial.controller.init;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.UserInfoDTO;
import com.eun.tutorial.dto.ZthhFileAttachDTO;
import com.eun.tutorial.service.UserService;
import com.eun.tutorial.service.ZthhFileAttachService;
import com.eun.tutorial.service.user.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class InitController {

        private static final Logger logger = LoggerFactory.getLogger(InitController.class);

        @GetMapping("/initInit")
        public ModelAndView init() {
                logger.debug("request url : /init");

                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("jsp/init/init");

                return modelAndView;
        }

        @PostMapping("/getUserProfile")
        public @ResponseBody Map<String, Object> getUserProfile(Authentication authentication){
        logger.debug("request url : /join");

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();

        Map<String, Object> res = new HashMap<>();
        res.put("userProfileImg", userDetailsImpl.getPicture());
        res.put("userName", userDetailsImpl.getUsername());

        return res;
        }
}
