# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('KatalystApp', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='appointment',
            name='datetime',
            field=models.CharField(default=None, max_length=512, null=True, blank=True),
        ),
        migrations.AlterField(
            model_name='reminders',
            name='datetime',
            field=models.CharField(default=None, max_length=512, null=True, blank=True),
        ),
    ]
