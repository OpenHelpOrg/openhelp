
    //private EmailConfig emailConfig;

//    // this constructor performs autowired DIa
//    public EmailController(EmailConfig emailConfig) {
//        this.emailConfig = emailConfig;
//    }

//    @PostMapping("/sendEmail")
//    public void sendFeedback() {
//        // create mail sender
//
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(this.emailConfig.getHost());
//        mailSender.setPort(this.emailConfig.getPort());
//        mailSender.setUsername(this.emailConfig.getUsername());
//        mailSender.setPassword(this.emailConfig.getPassword());
//
//        // create an email instance
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        // email.getEmail()
//        mailMessage.setFrom("admin@sportapp.bg");
//        mailMessage.setTo("christian.crousserkaiman@gmail.com");
//        // email.getSubject()
//        mailMessage.setSubject("Test subject");
//        // email.getMessageText()
//        mailMessage.setText("Test text !!!");
//
//        // send mail
//
//        mailSender.send(mailMessage);
//    }
//}