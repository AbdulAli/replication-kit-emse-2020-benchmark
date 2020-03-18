library(ggplot2)
library(ggpubr)
source("RQ3_Table.R")
#source("FixingDataset.R")

data = check

ma = data[data$technique == 'Ma12' & data$configuration != 4,]
ma$windowSize = as.character(ma$windowSize)
ma$windowSize = factor(ma$windowSize, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19))
ma$configuration[ma$configuration == 1] <- "CC"
ma$configuration[ma$configuration == 2] <- "IC"
ma$configuration[ma$configuration == 3] <- "CI"
ma$configuration = factor(ma$configuration, levels = c("CC","IC","CI"))
maPlot <- ggplot(ma, aes(x=ma$windowSize, y=ma$fscore, fill=as.character(ma$configuration))) + geom_boxplot() + facet_wrap(~as.character(ma$configuration)) + facet_grid(cols = vars(ma$configuration)) + theme(legend.position = "none") + theme(axis.text.x = element_text(angle = 90)) + geom_abline(aes(intercept=0.392, slope=0), color="#00bf7d", size=0.5) + xlab("windowSize") + ylab("F-Score") + ylim(min(ma$fscore),max(ma$fscore))

camruz = data[data$technique == 'CamargoCruz09' & data$configuration != 4,]
camruz$windowSize = as.character(camruz$windowSize)
camruz$windowSize = factor(camruz$windowSize, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19))
camruz$configuration[camruz$configuration == 1] <- "CC"
camruz$configuration[camruz$configuration == 2] <- "IC"
camruz$configuration[camruz$configuration == 3] <- "CI"
camruz$configuration = factor(camruz$configuration, levels = c("CC","IC","CI"))
camruzPlot <- ggplot(camruz, aes(x=camruz$windowSize, y=camruz$fscore, fill=as.character(camruz$configuration))) + geom_boxplot() +   facet_wrap(~as.character(camruz$configuration)) + facet_grid(cols = vars(camruz$configuration)) + theme(legend.position = "none") + theme(axis.text.x = element_text(angle = 90)) + geom_abline(aes(intercept=0.389, slope=0), color="#00bf7d", size=0.5) + xlab("windowSize") + ylab("F-Score") + ylim(min(camruz$fscore),max(camruz$fscore))

amasaki = data[data$technique == 'Amasaki15' & data$configuration != 4,]
amasaki$windowSize = as.character(amasaki$windowSize)
amasaki$windowSize = factor(amasaki$windowSize, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19))
amasaki$configuration[amasaki$configuration == 1] <- "CC"
amasaki$configuration[amasaki$configuration == 2] <- "IC"
amasaki$configuration[amasaki$configuration == 3] <- "CI"
amasaki$configuration = factor(amasaki$configuration, levels = c("CC","IC","CI"))
amasakiPlot <- ggplot(amasaki, aes(x=amasaki$windowSize, y=amasaki$fscore, fill=as.character(amasaki$configuration))) + geom_boxplot() +   facet_wrap(~as.character(amasaki$configuration)) + facet_grid(cols = vars(amasaki$configuration)) + theme(legend.position = "none") + theme(axis.text.x = element_text(angle = 90)) + geom_abline(aes(intercept=0.388, slope=0), color="#00bf7d", size=0.5) + xlab("windowSize") + ylab("F-Score") + ylim(min(amasaki$fscore),max(amasaki$fscore))

nam = data[data$technique == 'Nam15' & data$configuration != 4,]
nam$windowSize = as.character(nam$windowSize)
nam$windowSize = factor(nam$windowSize, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19))
nam$configuration[nam$configuration == 1] <- "CC"
nam$configuration[nam$configuration == 2] <- "IC"
nam$configuration[nam$configuration == 3] <- "CI"
nam$configuration = factor(nam$configuration, levels = c("CC","IC","CI"))
namPlot <- ggplot(nam, aes(x=nam$windowSize, y=nam$fscore, fill=as.character(nam$configuration))) + geom_boxplot() +   facet_wrap(~as.character(nam$configuration)) + facet_grid(cols = vars(nam$configuration)) + theme(legend.position = "none") + theme(axis.text.x = element_text(angle = 90)) + geom_abline(aes(intercept=0.492, slope=0), color="#00bf7d", size=0.5) + xlab("windowSize") + ylab("F-Score") + ylim(min(nam$fscore),max(nam$fscore))

watanabe = data[data$technique == 'Watanabe08' & data$configuration != 4,]
watanabe$windowSize = as.character(watanabe$windowSize)
watanabe$windowSize = factor(watanabe$windowSize, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19))
watanabe$configuration[watanabe$configuration == 1] <- "CC"
watanabe$configuration[watanabe$configuration == 2] <- "IC"
watanabe$configuration[watanabe$configuration == 3] <- "CI"
watanabe$configuration = factor(watanabe$configuration, levels = c("CC","IC","CI"))
watanabePlot <- ggplot(watanabe, aes(x=watanabe$windowSize, y=watanabe$fscore, fill=as.character(watanabe$configuration))) + geom_boxplot() +   facet_wrap(~as.character(watanabe$configuration)) + facet_grid(cols = vars(watanabe$configuration)) + theme(legend.position = "none") + theme(axis.text.x = element_text(angle = 90)) + geom_abline(aes(intercept=0.392, slope=0), color="#00bf7d", size=0.5) + xlab("windowSize") + ylab("F-Score") + ylim(min(watanabe$fscore),max(watanabe$fscore))


#II plot
ii = data[data$configuration == 4,]
ii <- data.frame(technique = ii$technique, configuration = ii$configuration, fscore = ii$fscore,stringsAsFactors = TRUE)
ii$technique = as.character(ii$technique)

ii$technique[ii$technique == 'Amasaki15'] <- "A"
ii$technique[ii$technique == 'Watanabe08'] <- "B"
ii$technique[ii$technique == 'CamargoCruz09'] <- "C"
ii$technique[ii$technique == 'Nam15'] <- "D"
ii$technique[ii$technique == 'Ma12'] <- "E"


ii$technique = factor(ii$technique, levels = c('A','B','C','D','E'))
ii$configuration[ii$configuration == 4] <- "II"
ii$configuration <- factor(ii$configuration, levels = c("II"))
iiPlot <- ggplot(ii, aes(x=ii$technique, y=ii$fscore, fill=as.character(ii$configuration))) + geom_boxplot() +   facet_wrap(~as.character(ii$configuration)) + facet_grid(cols = vars(ii$configuration)) + theme(legend.position = "none") + theme(axis.text.x = element_text(angle = 0))  + xlab("Technique") + ylab("F-Score") + ylim(min(ii$fscore),max(ii$fscore))

#pdfName <- "r1e.pdf"

#pdf(pdfName,8,11)
ggarrange(amasakiPlot + theme(axis.text.x = element_text(angle=90,size=5)) + rremove("x.title"),
          watanabePlot+ theme(axis.text.x = element_text(angle=90,size=5)) + rremove("x.title"),
          camruzPlot + theme(axis.text.x = element_text(angle=90,size=5)) + rremove("x.title"),
          namPlot+ theme(axis.text.x = element_text(angle=90,size=5)) + rremove("x.title"),
          maPlot + theme(axis.text.x = element_text(angle=90,size=5)) + xlab("Window Size"),
          iiPlot,
          labels = c("A", "B", "C","D", "E"), 
          ncol = 1, nrow = 6)
#dev.off()



