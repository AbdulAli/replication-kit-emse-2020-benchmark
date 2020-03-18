library(ggplot2)
library(ggpubr)
source("RQ3_Table.R")
#source("FixingDataset.R")

data = check

ma = data[data$technique == 'Ma12',]
ma$time = as.character(ma$time)
ma$time = factor(ma$time, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18))
ma$configuration[ma$configuration == 1] <- "CC"
ma$configuration[ma$configuration == 2] <- "IC"
ma$configuration[ma$configuration == 3] <- "CI"
ma$configuration[ma$configuration == 4] <- "II"
ma$configuration = factor(ma$configuration, levels = c("CC","IC","CI","II"))
maPlot <- ggplot(ma, aes(x=ma$time, y=ma$fscore, fill=as.character(ma$configuration))) + geom_boxplot() +   facet_wrap(~as.character(ma$configuration)) + facet_grid(cols = vars(ma$configuration)) + theme(legend.position = "none") + theme(axis.text.x = element_text(angle = 90)) + geom_abline(aes(intercept=0.392, slope=0), color="#00bf7d", size=0.5) + xlab("Time") + ylab("F-Score") + ylim(min(ma$fscore),max(ma$fscore))

camruz = data[data$technique == 'CamargoCruz09',]
camruz$time = as.character(camruz$time)
camruz$time = factor(camruz$time, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18))
camruz$configuration[camruz$configuration == 1] <- "CC"
camruz$configuration[camruz$configuration == 2] <- "IC"
camruz$configuration[camruz$configuration == 3] <- "CI"
camruz$configuration[camruz$configuration == 4] <- "II"
camruz$configuration = factor(camruz$configuration, levels = c("CC","IC","CI","II"))
camruzPlot <- ggplot(camruz, aes(x=camruz$time, y=camruz$fscore, fill=as.character(camruz$configuration))) + geom_boxplot() +   facet_wrap(~as.character(camruz$configuration)) + facet_grid(cols = vars(camruz$configuration)) + theme(legend.position = "none") + theme(axis.text.x = element_text(angle = 90)) + geom_abline(aes(intercept=0.389, slope=0), color="#00bf7d", size=0.5) + xlab("Time") + ylab("F-Score") + ylim(min(camruz$fscore),max(camruz$fscore))

amasaki = data[data$technique == 'Amasaki15',]
amasaki$time = as.character(amasaki$time)
amasaki$time = factor(amasaki$time, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18))
amasaki$configuration[amasaki$configuration == 1] <- "CC"
amasaki$configuration[amasaki$configuration == 2] <- "IC"
amasaki$configuration[amasaki$configuration == 3] <- "CI"
amasaki$configuration[amasaki$configuration == 4] <- "II"
amasaki$configuration = factor(amasaki$configuration, levels = c("CC","IC","CI","II"))
amasakiPlot <- ggplot(amasaki, aes(x=amasaki$time, y=amasaki$fscore, fill=as.character(amasaki$configuration))) + geom_boxplot() +   facet_wrap(~as.character(amasaki$configuration)) + facet_grid(cols = vars(amasaki$configuration)) + theme(legend.position = "none") + theme(axis.text.x = element_text(angle = 90)) + geom_abline(aes(intercept=0.388, slope=0), color="#00bf7d", size=0.5) + xlab("Time") + ylab("F-Score") + ylim(min(amasaki$fscore),max(amasaki$fscore))



nam = data[data$technique == 'Nam15',]
nam$time = as.character(nam$time)
nam$time = factor(nam$time, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18))
nam$configuration[nam$configuration == 1] <- "CC"
nam$configuration[nam$configuration == 2] <- "IC"
nam$configuration[nam$configuration == 3] <- "CI"
nam$configuration[nam$configuration == 4] <- "II"
nam$configuration = factor(nam$configuration, levels = c("CC","IC","CI","II"))
namPlot <- ggplot(nam, aes(x=nam$time, y=nam$fscore, fill=as.character(nam$configuration))) + geom_boxplot() +   facet_wrap(~as.character(nam$configuration)) + facet_grid(cols = vars(nam$configuration)) + theme(legend.position = "none") + theme(axis.text.x = element_text(angle = 90)) + geom_abline(aes(intercept=0.492, slope=0), color="#00bf7d", size=0.5) + xlab("Time") + ylab("F-Score") + ylim(min(nam$fscore),max(nam$fscore))

watanabe = data[data$technique == 'Watanabe08',]
watanabe$time = as.character(watanabe$time)
watanabe$time = factor(watanabe$time, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18))
watanabe$configuration[watanabe$configuration == 1] <- "CC"
watanabe$configuration[watanabe$configuration == 2] <- "IC"
watanabe$configuration[watanabe$configuration == 3] <- "CI"
watanabe$configuration[watanabe$configuration == 4] <- "II"
watanabe$configuration = factor(watanabe$configuration, levels = c("CC","IC","CI","II"))
watanabePlot <- ggplot(watanabe, aes(x=watanabe$time, y=watanabe$fscore, fill=as.character(watanabe$configuration))) + geom_boxplot() +   facet_wrap(~as.character(watanabe$configuration)) + facet_grid(cols = vars(watanabe$configuration)) + theme(legend.position = "none") + theme(axis.text.x = element_text(angle = 90)) + geom_abline(aes(intercept=0.392, slope=0), color="#00bf7d", size=0.5) + xlab("Time") + ylab("F-Score") + ylim(min(watanabe$fscore),max(watanabe$fscore))

ggarrange(amasakiPlot + theme(axis.text.x = element_text(angle=90,size=5)) + rremove("x.title"),
          watanabePlot+ theme(axis.text.x = element_text(angle=90,size=5)) + rremove("x.title"),
          camruzPlot + theme(axis.text.x = element_text(angle=90,size=5)) + rremove("x.title"),
          namPlot+ theme(axis.text.x = element_text(angle=90,size=5)) + rremove("x.title"),
          maPlot + theme(axis.text.x = element_text(angle=90,size=5)) + xlab("Split point in time"),
          labels = c("A", "B", "C","D", "E"), 
          ncol = 1, nrow = 5)




