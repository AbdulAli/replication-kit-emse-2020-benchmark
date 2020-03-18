
#Window wise sd for each technique and config
windowSd<-summarise_at(group_by(check,technique,configuration,windowSize),vars(fscore),funs(sd(.,na.rm=TRUE)))
#windowSd[windowSd$configuration==1,]$windowSize = factor(windowSd[windowSd$configuration==1,]$windowSize, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18))
config1Sd <- ggplot(windowSd[windowSd$configuration==1,], aes(windowSize, fscore, colour = technique)) + labs(colour = "Techniques",linetype = "Techniques") 
config1Sd <- config1Sd + geom_line(aes(linetype = technique)) + xlab('Window Size') + ylab('Fscore Sd') + scale_x_continuous(breaks = seq(1, 19, 1))

config2Sd <- ggplot(windowSd[windowSd$configuration==2,], aes(windowSize, fscore, colour = technique)) + labs(colour = "Techniques",linetype = "Techniques")
config2Sd <- config2Sd + geom_line(aes(linetype = technique)) + xlab('Window Size') + ylab('Fscore Sd') + scale_x_continuous(breaks = seq(1, 19, 1))

config3Sd <- ggplot(windowSd[windowSd$configuration==3,], aes(windowSize, fscore, colour = technique)) + labs(colour = "Techniques",linetype = "Techniques")
config3Sd <- config3Sd + geom_line(aes(linetype = technique)) + xlab('Window Size') + ylab('Fscore Sd') + scale_x_continuous(breaks = seq(1, 19, 1))

config4Sd <- ggplot(windowSd[windowSd$configuration==4,], aes(technique, fscore)) 
config4Sd <- config4Sd + geom_point(aes(size=2)) + xlab('Techniques') + ylab('Fscore Sd') + theme(legend.position = "none")

ggarrange(config1Sd + theme(axis.text.x = element_text(angle=90,size=5)) + theme(legend.position = "top"),
          config2Sd+ theme(axis.text.x = element_text(angle=90,size=5))  + theme(legend.position = "none"),
          config3Sd + theme(axis.text.x = element_text(angle=90,size=5))  + theme(legend.position = "none"),
          config4Sd+ theme(axis.text.x = element_text(angle=0,size=5)) ,
          labels = c("CC", "IC", "CI","II"), 
          ncol = 1, nrow = 4)
